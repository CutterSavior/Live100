package com.lanjii.biz.admin.tool.service;

import com.lanjii.biz.admin.tool.model.vo.ToolFileVO;
import com.lanjii.core.base.BaseService;
import com.lanjii.biz.admin.tool.model.entity.ToolFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * 系统工具-文件管理表(ToolFile)表服务接口
 *
 * @author lanjii
 */
public interface ToolFileService extends BaseService<ToolFile> {

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件信息
     */
    ToolFileVO uploadFile(MultipartFile file);

    /**
     * 根据ID获取文件详情
     *
     * @param id 文件ID
     * @return 文件信息
     */
    ToolFileVO getByIdNew(Long id);

}
