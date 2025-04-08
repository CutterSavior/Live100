package com.lanjii.service;

import com.lanjii.core.base.IBaseService;
import com.lanjii.model.entity.UploadFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lizheng
 * @since 2024-10-09
 */
public interface IUploadFileService extends IBaseService<UploadFile> {

    UploadFile upload(MultipartFile file);
}
