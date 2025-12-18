package com.lanjii.biz.admin.tool.service.impl;

import com.lanjii.biz.admin.tool.dao.ToolFileDao;
import com.lanjii.biz.admin.tool.model.entity.ToolFile;
import com.lanjii.biz.admin.tool.model.vo.ToolFileVO;
import com.lanjii.biz.admin.tool.service.ToolFileService;
import com.lanjii.common.enums.FileCategory;
import com.lanjii.common.util.FileUtils;
import com.lanjii.core.base.BaseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 系统工具-文件管理表(ToolFile)表服务实现类
 *
 * @author lanjii
 */
@RequiredArgsConstructor
@Service("toolFileService")
public class ToolFileServiceImpl extends BaseServiceImpl<ToolFileDao, ToolFile> implements ToolFileService {

    @Override
    public ToolFileVO uploadFile(MultipartFile file) {
        try {
            // 业务逻辑验证
            if (file.isEmpty()) {
                throw new RuntimeException("上传文件不能为空");
            }

            String originalFilename = file.getOriginalFilename();

            String savedFilePath = FileUtils.saveFileWithDatePath(file);

            String fileExtension = FileUtils.getFileExtensionWithDot(originalFilename);
            FileCategory fileCategory = FileUtils.detectFileCategory(originalFilename);

            // 创建文件记录
            ToolFile toolFile = new ToolFile();
            toolFile.setOriginalName(originalFilename);
            toolFile.setFilePath(savedFilePath);
            toolFile.setFileSize(file.getSize());
            toolFile.setFileType(fileCategory.name());
            toolFile.setFileExtension(fileExtension);

            this.save(toolFile);

            return ToolFile.INSTANCE.toVo(toolFile);

        } catch (IOException e) {
            throw new RuntimeException("文件上传失败", e);
        }
    }

    @Override
    public ToolFileVO getByIdNew(Long id) {
        ToolFile entity = this.getById(id);
        return ToolFile.INSTANCE.toVo(entity);
    }

}
