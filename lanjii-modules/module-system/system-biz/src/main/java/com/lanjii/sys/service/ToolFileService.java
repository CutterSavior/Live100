package com.lanjii.sys.service;

import com.lanjii.system.api.vo.ToolFileVO;
import com.lanjii.framework.mp.base.BaseService;
import com.lanjii.sys.entity.ToolFile;
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
