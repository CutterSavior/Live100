package com.lanjii.util;

import com.lanjii.core.enums.ResultCode;
import com.lanjii.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件工具
 *
 * @author LiZheng
 * @date 2024-10-09
 */
@Slf4j
public class FileUtils {

    private static final String USER_DIR = System.getProperty("user.dir");
    private static final Path TARGET_DIR = Paths.get(USER_DIR, "upload");

    public static String upload(MultipartFile file, String fileName) {
        try {

            String targetPath = MyDateUtils.nowDate() + File.separator + fileName;

            Path targetFile = TARGET_DIR.resolve(targetPath);
            Files.createDirectories(targetFile.getParent());
            file.transferTo(targetFile.toFile());

            return targetPath;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(ResultCode.INTERNAL_SERVER_ERROR, "UploadFile conversion failed!");
        }
    }

    public static String upload(String fileName, String fileContent) {
        try {
            String absolutePath = createAbsolutePath(fileName);
            try (FileOutputStream fos = new FileOutputStream(absolutePath)) {
                fos.write(fileContent.getBytes());
                fos.flush(); // 确保所有内容都写入
            }

            return absolutePath;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(ResultCode.INTERNAL_SERVER_ERROR, "UploadFile conversion failed!");
        }
    }

    public static String zipFile(String zipFileName, List<String> files) {

        try {
            String absolutePath = createAbsolutePath(zipFileName);
            try (FileOutputStream fos = new FileOutputStream(createAbsolutePath(zipFileName));
                 ZipOutputStream zipOut = new ZipOutputStream(fos)) {

                for (String file : files) {
                    Path fileToZip = Path.of(file);
                    ZipEntry zipEntry = new ZipEntry(fileToZip.getFileName().toString());
                    zipOut.putNextEntry(zipEntry);
                    Files.copy(fileToZip, zipOut);
                    zipOut.closeEntry();
                }
                return absolutePath;
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(ResultCode.INTERNAL_SERVER_ERROR, "Failed to zip file!");
        }
    }

    public static String createAbsolutePath(String fileName) throws IOException {
        String targetPath = MyDateUtils.nowDate() + File.separator + fileName;
        Path targetFile = TARGET_DIR.resolve(targetPath);
        Files.createDirectories(targetFile.getParent());
        return TARGET_DIR + File.separator + targetPath;
    }

}
