package com.lanjii.biz.admin.tool.controller;

import com.github.pagehelper.PageHelper;
import com.lanjii.core.base.PageParam;
import com.lanjii.core.base.support.PageData;
import com.lanjii.core.resp.R;
import com.lanjii.core.resp.ResultCode;
import com.lanjii.biz.admin.tool.model.dto.ToolFileDTO;
import com.lanjii.biz.admin.tool.model.entity.ToolFile;
import com.lanjii.biz.admin.tool.service.ToolFileService;
import com.lanjii.common.util.FileUtils;
import com.lanjii.common.util.PageDataUtils;
import com.lanjii.biz.admin.tool.model.vo.ToolFileVO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * 系统工具/文件管理
 *
 * @author lanjii
 */
@RestController
@RequestMapping("/admin/tool/files")
@RequiredArgsConstructor
public class ToolFileController {

    private final ToolFileService toolFileService;

    /**
     * 上传文件
     *
     * @param file 文件
     */
    @PostMapping("/upload")
    public R<ToolFileVO> uploadFile(@RequestParam("file") MultipartFile file) {
        ToolFileVO fileVO = toolFileService.uploadFile(file);
        return R.success(fileVO);
    }

    /**
     * 查询详情
     *
     * @param id 文件ID
     */
    @GetMapping("/{id}")
    public R<ToolFileVO> getById(@PathVariable Long id) {
        ToolFileVO vo = toolFileService.getByIdNew(id);
        return R.success(vo);
    }

    /**
     * 分页查询
     *
     * @param pageParam 分页查询参数
     * @param filter    查询条件
     */
    @GetMapping
    public R<PageData<ToolFileVO>> page(PageParam pageParam, ToolFileDTO filter) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<ToolFile> toolFileList = toolFileService.listByFilter(filter);
        return R.success(PageDataUtils.make(toolFileList, ToolFile.INSTANCE));
    }

    /**
     * 下载文件
     *
     * @param id 文件ID
     */
    @GetMapping("/{id}/download")
    public R<Void> downloadFile(@PathVariable Long id) throws IOException {
        ToolFileVO fileVO = toolFileService.getByIdNew(id);
        if (fileVO == null) {
            return R.fail(ResultCode.NOT_FOUND, "文件不存在");
        }

        // 拼接完整的文件系统路径：上传根目录 + 相对路径
        Path filePath = Paths.get(FileUtils.DEFAULT_UPLOAD_PATH, fileVO.getFilePath());
        Resource resource = new FileSystemResource(filePath);

        if (!resource.exists()) {
            return R.fail(ResultCode.NOT_FOUND, "文件不存在");
        }

        String contentType = Files.probeContentType(filePath);

        R.writeFile(resource, fileVO.getOriginalName(), contentType);
        return R.success();
    }


}
