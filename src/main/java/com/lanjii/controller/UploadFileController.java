package com.lanjii.controller;

import com.lanjii.model.entity.UploadFile;
import com.lanjii.core.obj.R;
import com.lanjii.service.IUploadFileService;
import com.lanjii.model.vo.UploadFileVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author LiZheng
 * @date 2024-10-09
 */
@RestController
@RequestMapping("/admin/file")
@RequiredArgsConstructor
public class UploadFileController {

    private final IUploadFileService fileService;

    @PostMapping("/upload")
    public R<UploadFileVo> upload(MultipartFile file) {
        UploadFile uploadFile = fileService.upload(file);
        return R.success(UploadFile.INSTANCE.modelToVo(uploadFile));
    }

}
