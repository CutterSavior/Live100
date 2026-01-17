package com.lanjii.biz.admin.ai.service.impl;

import com.lanjii.biz.admin.ai.dao.AiFileKnowledgeDao;
import com.lanjii.biz.admin.ai.model.dto.AiFileKnowledgeDTO;
import com.lanjii.biz.admin.ai.model.entity.AiFileKnowledge;
import com.lanjii.biz.admin.ai.model.vo.AiFileKnowledgeVO;
import com.lanjii.biz.admin.ai.service.AiFileKnowledgeService;
import com.lanjii.biz.admin.ai.service.AiMetadataFieldService;
import com.lanjii.biz.admin.ai.strategy.DocumentParserStrategy;
import com.lanjii.biz.admin.ai.strategy.DocumentParserStrategyFactory;
import com.lanjii.common.exception.BizException;
import com.lanjii.common.util.FileUtils;
import com.lanjii.common.util.JacksonUtils;
import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.core.resp.ResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * AI文件知识库表(AiFileKnowledge)表服务实现类
 *
 * @author lanjii
 */
@Slf4j
@RequiredArgsConstructor
@Service("aiFileKnowledgeService")
public class AiFileKnowledgeServiceImpl extends BaseServiceImpl<AiFileKnowledgeDao, AiFileKnowledge> implements AiFileKnowledgeService {

    private final VectorStore vectorStore;
    private final AiMetadataFieldService aiMetadataFieldService;
    private final DocumentParserStrategyFactory documentParserStrategyFactory;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveNew(AiFileKnowledgeDTO dto) {

        MultipartFile file = dto.getFile();
        if (file == null || file.isEmpty()) {
            throw new BizException("文件不能为空");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename.isEmpty()) {
            throw new BizException("文件名不能为空");
        }

        String fileType = FileUtils.getFileExtension(originalFilename);
        if (!documentParserStrategyFactory.isSupportedFileType(fileType)) {
            throw new BizException("不支持的文件类型: " + fileType);
        }

        try {
            Pair<String, String> fileWithDatePathDetail = FileUtils.saveFileWithDatePathDetail(file);
            String content = parseFileContent(fileWithDatePathDetail.getLeft(), fileType);

            AiFileKnowledge entity = new AiFileKnowledge();
            entity.setFileName(originalFilename);
            entity.setFileType(fileType);
            entity.setFilePath(fileWithDatePathDetail.getRight());
            entity.setFullPath(fileWithDatePathDetail.getLeft());
            entity.setFileSize(file.getSize());
            entity.setMetadataJson(dto.getMetadataJson());

            Set<String> newKeys = AiMetadataFieldServiceImpl.extractMetadataKeys(entity.getMetadataJson());
            save(entity);

            if (!newKeys.isEmpty()) {
                aiMetadataFieldService.incrementUseCount(newKeys);
            }

            Map<String, Object> metadata = JacksonUtils.toMap(entity.getMetadataJson(), String.class, Object.class);
            vectorStore.add(Collections.singletonList(new Document(String.valueOf(entity.getId()), content, metadata)));
        } catch (IOException e) {
            throw new BizException("文件上传失败");

        }

    }

    @Override
    public AiFileKnowledgeVO getByIdNew(Long id) {
        AiFileKnowledge entity = getById(id);
        if (entity == null) {
            throw new BizException(ResultCode.NOT_FOUND, "数据不存在");
        }

        return AiFileKnowledge.INSTANCE.toVo(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateByIdNew(Long id, AiFileKnowledgeDTO dto) {
        AiFileKnowledge existEntity = getById(id);
        if (existEntity == null) {
            throw new BizException(ResultCode.NOT_FOUND, "数据不存在");
        }

        // 计算元数据 key 集合的变化
        Set<String> oldKeys = AiMetadataFieldServiceImpl.extractMetadataKeys(existEntity.getMetadataJson());
        Set<String> newKeys = AiMetadataFieldServiceImpl.extractMetadataKeys(dto.getMetadataJson());

        Set<String> toDecrement = new HashSet<>(oldKeys);
        toDecrement.removeAll(newKeys);
        if (!toDecrement.isEmpty()) {
            aiMetadataFieldService.decrementUseCount(toDecrement);
        }

        Set<String> toIncrement = new HashSet<>(newKeys);
        toIncrement.removeAll(oldKeys);
        if (!toIncrement.isEmpty()) {
            aiMetadataFieldService.incrementUseCount(toIncrement);
        }

        // 更新元数据
        existEntity.setMetadataJson(dto.getMetadataJson());

        // 删除旧向量
        vectorStore.delete(List.of(String.valueOf(id)));

        if (dto.getFile() != null && !dto.getFile().isEmpty()) {

            deleteFileFromDisk(existEntity.getFullPath());

            // 保存新文件
            String originalFilename = dto.getFile().getOriginalFilename();
            String fileType = FileUtils.getFileExtension(originalFilename);
            if (!documentParserStrategyFactory.isSupportedFileType(fileType)) {
                throw new BizException("不支持的文件类型: " + fileType);
            }

            try {
                Pair<String, String> fileWithDatePathDetail = FileUtils.saveFileWithDatePathDetail(dto.getFile());
                String fullPath = fileWithDatePathDetail.getLeft();
                String accessPath = fileWithDatePathDetail.getRight();

                existEntity.setFileName(originalFilename);
                existEntity.setFileType(fileType);
                existEntity.setFilePath(accessPath);
                existEntity.setFullPath(fullPath);
                existEntity.setFileSize(dto.getFile().getSize());
            } catch (IOException e) {
                throw new BizException("文件上传失败");
            }
        }
        updateById(existEntity);

        String content = parseFileContent(existEntity.getFullPath(), existEntity.getFileType());
        Map<String, Object> metadata = JacksonUtils.toMap(existEntity.getMetadataJson(), String.class, Object.class);
        vectorStore.add(List.of(
                new Document(String.valueOf(existEntity.getId()), content, metadata)
        ));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByIdNew(Long id) {
        AiFileKnowledge entity = getById(id);
        if (entity == null) {
            throw new BizException(ResultCode.NOT_FOUND, "数据不存在");
        }

        Set<String> oldKeys = AiMetadataFieldServiceImpl.extractMetadataKeys(entity.getMetadataJson());
        if (!oldKeys.isEmpty()) {
            aiMetadataFieldService.decrementUseCount(oldKeys);
        }

        // 删除文件
        deleteFileFromDisk(entity.getFullPath());

        // 删除数据库记录
        removeById(id);

        // 删除向量
        vectorStore.delete(List.of(String.valueOf(id)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rebuildAllVectors() {
        List<AiFileKnowledge> list = list();
        if (list == null || list.isEmpty()) {
            return;
        }

        // 删除所有旧向量
        List<String> ids = list.stream()
                .map(k -> String.valueOf(k.getId()))
                .collect(Collectors.toList());
        vectorStore.delete(ids);

        // 从文件重新解析并生成向量
        List<Document> documents = list.stream().map(entity -> {
                    String content = parseFileContent(entity.getFullPath(), entity.getFileType());
                    Map<String, Object> metadata = JacksonUtils.toMap(entity.getMetadataJson(), String.class, Object.class);
                    return new Document(String.valueOf(entity.getId()), content, metadata);
                })
                .collect(Collectors.toList());

        if (!documents.isEmpty()) {
            vectorStore.add(documents);
        }

        // 重新计算元数据使用次数
        Map<String, Long> metadataUsageMap = new HashMap<>();
        for (AiFileKnowledge knowledge : list) {
            Set<String> keys = AiMetadataFieldServiceImpl.extractMetadataKeys(knowledge.getMetadataJson());
            keys.forEach(key -> metadataUsageMap.merge(key, 1L, Long::sum));
        }
        aiMetadataFieldService.recalculateUseCount(metadataUsageMap);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rebuildVectorById(Long id) {
        AiFileKnowledge entity = getById(id);
        if (entity == null) {
            throw new BizException(ResultCode.NOT_FOUND, "文件知识库数据不存在");
        }

        vectorStore.delete(List.of(String.valueOf(id)));

        String content = parseFileContent(entity.getFullPath(), entity.getFileType());
        Map<String, Object> metadata = JacksonUtils.toMap(entity.getMetadataJson(), String.class, Object.class);
        vectorStore.add(List.of(
                new Document(String.valueOf(entity.getId()), content, metadata)
        ));
    }

    /**
     * 从磁盘删除文件
     */
    private void deleteFileFromDisk(String filePath) {
        if (filePath != null && !filePath.isEmpty()) {
            boolean deleted = FileUtils.deleteFile(filePath);
            if (!deleted) {
                log.warn("删除文件失败或文件不存在: {}", filePath);
            }
        }
    }

    /**
     * 解析文件内容
     */
    private String parseFileContent(String filePath, String fileType) {
        try {
            Resource resource = new FileSystemResource(filePath);
            DocumentParserStrategy strategy = documentParserStrategyFactory.getStrategy(fileType);
            return strategy.parse(resource);
        } catch (Exception e) {
            log.error("解析文件内容失败: {}", filePath, e);
            throw new BizException("解析文件内容失败: " + e.getMessage());
        }
    }


}
