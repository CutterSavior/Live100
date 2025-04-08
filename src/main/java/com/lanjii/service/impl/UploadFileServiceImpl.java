package com.lanjii.service.impl;

import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.dao.UploadFileMapper;
import com.lanjii.model.entity.UploadFile;
import com.lanjii.service.IUploadFileService;
import com.lanjii.util.FileUtils;
import com.lanjii.util.IdUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lizheng
 * @since 2024-10-09
 */
@Service
public class UploadFileServiceImpl extends BaseServiceImpl<UploadFileMapper, UploadFile> implements IUploadFileService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UploadFile upload(MultipartFile file) {

        String originalFilename = file.getOriginalFilename();
        String fileType = originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());
        String simpleId = IdUtils.simpleId();
        String path = FileUtils.upload(file, simpleId + "." + fileType);
        UploadFile f = new UploadFile();
        f.setFileName(originalFilename);
        f.setFilePath(path);
        this.save(f);
        return f;
    }
}
