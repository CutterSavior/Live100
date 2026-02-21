package com.lanjii.sys.service.impl;

import com.lanjii.sys.dao.ToolFileDao;
import com.lanjii.sys.service.ToolFileService;
import com.lanjii.sys.entity.ToolFile;
import com.lanjii.system.api.vo.ToolFileVO;
import com.lanjii.common.enums.FileCategory;
import com.lanjii.framework.web.util.FileUtils;
import com.lanjii.framework.mp.base.BaseServiceImpl;
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
