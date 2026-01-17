package com.lanjii.common.util;

import com.lanjii.common.enums.FileCategory;
import com.lanjii.config.FileProperties;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 文件工具类
 * 提供文件类型识别、文件上传等通用文件处理方法
 */
public final class FileUtils {

    public static String DEFAULT_UPLOAD_PATH = FileProperties.getUploadPath();

    private FileUtils() {
    }

    /**
     * 扩展名到文件类型大类的映射
     */
    private static final Map<String, FileCategory> EXTENSION_TYPE_MAP = new HashMap<>();

    static {
        // 图片类型
        EXTENSION_TYPE_MAP.put(".jpg", FileCategory.IMAGE);
        EXTENSION_TYPE_MAP.put(".jpeg", FileCategory.IMAGE);
        EXTENSION_TYPE_MAP.put(".png", FileCategory.IMAGE);
        EXTENSION_TYPE_MAP.put(".gif", FileCategory.IMAGE);
        EXTENSION_TYPE_MAP.put(".bmp", FileCategory.IMAGE);
        EXTENSION_TYPE_MAP.put(".webp", FileCategory.IMAGE);
        EXTENSION_TYPE_MAP.put(".svg", FileCategory.IMAGE);
        EXTENSION_TYPE_MAP.put(".ico", FileCategory.IMAGE);
        EXTENSION_TYPE_MAP.put(".tiff", FileCategory.IMAGE);
        EXTENSION_TYPE_MAP.put(".tif", FileCategory.IMAGE);

        // 视频类型
        EXTENSION_TYPE_MAP.put(".mp4", FileCategory.VIDEO);
        EXTENSION_TYPE_MAP.put(".avi", FileCategory.VIDEO);
        EXTENSION_TYPE_MAP.put(".mov", FileCategory.VIDEO);
        EXTENSION_TYPE_MAP.put(".wmv", FileCategory.VIDEO);
        EXTENSION_TYPE_MAP.put(".flv", FileCategory.VIDEO);
        EXTENSION_TYPE_MAP.put(".mkv", FileCategory.VIDEO);
        EXTENSION_TYPE_MAP.put(".webm", FileCategory.VIDEO);
        EXTENSION_TYPE_MAP.put(".mpeg", FileCategory.VIDEO);
        EXTENSION_TYPE_MAP.put(".mpg", FileCategory.VIDEO);
        EXTENSION_TYPE_MAP.put(".m4v", FileCategory.VIDEO);

        // 音频类型
        EXTENSION_TYPE_MAP.put(".mp3", FileCategory.AUDIO);
        EXTENSION_TYPE_MAP.put(".wav", FileCategory.AUDIO);
        EXTENSION_TYPE_MAP.put(".aac", FileCategory.AUDIO);
        EXTENSION_TYPE_MAP.put(".ogg", FileCategory.AUDIO);
        EXTENSION_TYPE_MAP.put(".flac", FileCategory.AUDIO);
        EXTENSION_TYPE_MAP.put(".m4a", FileCategory.AUDIO);
        EXTENSION_TYPE_MAP.put(".wma", FileCategory.AUDIO);
        EXTENSION_TYPE_MAP.put(".ape", FileCategory.AUDIO);

        // 文档类型
        EXTENSION_TYPE_MAP.put(".doc", FileCategory.DOCUMENT);
        EXTENSION_TYPE_MAP.put(".docx", FileCategory.DOCUMENT);
        EXTENSION_TYPE_MAP.put(".xls", FileCategory.DOCUMENT);
        EXTENSION_TYPE_MAP.put(".xlsx", FileCategory.DOCUMENT);
        EXTENSION_TYPE_MAP.put(".ppt", FileCategory.DOCUMENT);
        EXTENSION_TYPE_MAP.put(".pptx", FileCategory.DOCUMENT);
        EXTENSION_TYPE_MAP.put(".pdf", FileCategory.DOCUMENT);
        EXTENSION_TYPE_MAP.put(".rtf", FileCategory.DOCUMENT);

        // 压缩文件
        EXTENSION_TYPE_MAP.put(".zip", FileCategory.ARCHIVE);
        EXTENSION_TYPE_MAP.put(".rar", FileCategory.ARCHIVE);
        EXTENSION_TYPE_MAP.put(".7z", FileCategory.ARCHIVE);
        EXTENSION_TYPE_MAP.put(".tar", FileCategory.ARCHIVE);
        EXTENSION_TYPE_MAP.put(".gz", FileCategory.ARCHIVE);
        EXTENSION_TYPE_MAP.put(".bz2", FileCategory.ARCHIVE);
        EXTENSION_TYPE_MAP.put(".xz", FileCategory.ARCHIVE);

        // 文本类型
        EXTENSION_TYPE_MAP.put(".txt", FileCategory.TEXT);
        EXTENSION_TYPE_MAP.put(".csv", FileCategory.TEXT);
        EXTENSION_TYPE_MAP.put(".json", FileCategory.TEXT);
        EXTENSION_TYPE_MAP.put(".xml", FileCategory.TEXT);
        EXTENSION_TYPE_MAP.put(".html", FileCategory.TEXT);
        EXTENSION_TYPE_MAP.put(".htm", FileCategory.TEXT);
        EXTENSION_TYPE_MAP.put(".css", FileCategory.TEXT);
        EXTENSION_TYPE_MAP.put(".js", FileCategory.TEXT);
        EXTENSION_TYPE_MAP.put(".java", FileCategory.TEXT);
        EXTENSION_TYPE_MAP.put(".py", FileCategory.TEXT);
        EXTENSION_TYPE_MAP.put(".cpp", FileCategory.TEXT);
        EXTENSION_TYPE_MAP.put(".c", FileCategory.TEXT);
        EXTENSION_TYPE_MAP.put(".md", FileCategory.TEXT);
        EXTENSION_TYPE_MAP.put(".log", FileCategory.TEXT);
    }

    /**
     * 根据文件路径识别文件类型大类
     *
     * @param filePath 文件路径
     * @return 文件类型大类，无法识别时返回 OTHER
     */
    public static FileCategory detectFileCategory(Path filePath) {
        if (filePath == null) {
            return FileCategory.OTHER;
        }
        String fileName = filePath.getFileName().toString();
        return detectFileCategory(fileName);
    }

    /**
     * 根据文件名识别文件类型大类
     *
     * @param fileName 文件名
     * @return 文件类型大类，无法识别时返回 OTHER
     */
    public static FileCategory detectFileCategory(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return FileCategory.OTHER;
        }
        String extension = getFileExtension(fileName);
        return detectFileCategoryByExtension(extension);
    }


    /**
     * 通过文件扩展名识别文件类型大类
     *
     * @param extension 文件扩展名（不带点）
     * @return 文件类型大类，无法识别时返回 OTHER
     */
    public static FileCategory detectFileCategoryByExtension(String extension) {
        if (extension == null || extension.isEmpty()) {
            return FileCategory.OTHER;
        }

        FileCategory category = EXTENSION_TYPE_MAP.get(extension.toLowerCase());
        return category != null ? category : FileCategory.OTHER;
    }


    /**
     * 从文件名中提取扩展名
     *
     * @param fileName 文件名
     * @return 扩展名（小写，不带点），如果没有扩展名返回空字符串
     */
    public static String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }

        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == fileName.length() - 1) {
            return "";
        }

        return fileName.substring(lastDotIndex + 1).toLowerCase();
    }


    /**
     * 获取带点的文件扩展名（如：.jpg、.png）
     *
     * @param fileName 文件名
     * @return 带点的扩展名，如果没有扩展名返回空字符串
     */
    public static String getFileExtensionWithDot(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }

        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == fileName.length() - 1) {
            return "";
        }

        return fileName.substring(lastDotIndex);
    }

    /**
     * 获取文件类型的中文描述
     *
     * @param category 文件类型大类
     * @return 中文描述
     */
    public static String getCategoryDescription(FileCategory category) {
        return category != null ? category.getDescription() : "未知";
    }


    /**
     * 生成唯一的文件名（UUID + 原始扩展名）
     *
     * @param originalFileName 原始文件名
     * @return 唯一文件名
     */
    public static String generateUniqueFileName(String originalFileName) {
        String extension = getFileExtensionWithDot(originalFileName);
        return UUID.randomUUID() + extension;
    }

    /**
     * 确保目录存在，如果不存在则创建
     *
     * @param directory 目录路径
     * @throws IOException 创建目录失败时抛出
     */
    public static void ensureDirectoryExists(Path directory) throws IOException {
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }
    }

    /**
     * 确保目录存在，如果不存在则创建
     *
     * @param directory 目录路径字符串
     * @throws IOException 创建目录失败时抛出
     */
    public static void ensureDirectoryExists(String directory) throws IOException {
        ensureDirectoryExists(Paths.get(directory));
    }

    /**
     * 保存上传的文件到指定目录
     *
     * @param file      上传的文件
     * @param directory 目标目录
     * @param fileName  保存的文件名
     * @return 保存后的文件路径
     * @throws IOException 保存文件失败时抛出
     */
    public static String saveFile(MultipartFile file, String directory, String fileName) throws IOException {
        Path targetDir = Paths.get(directory).normalize();
        ensureDirectoryExists(targetDir);

        Path targetPath = targetDir.resolve(fileName);
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        return targetPath.toString();
    }

    public static String getDiskFullPath(String targetPath) {
        return "";
    }

    /**
     * 保存上传的文件到指定目录，按照日期自动创建子目录（自动生成唯一文件名）
     * 目录结构：directory/yyyy/MM/dd/
     *
     * @param file      上传的文件
     * @param directory 目标根目录
     * @return Pair 对象，Left 为物理完整路径，Right 为访问路径
     * @throws IOException 保存文件失败时抛出
     */
    public static Pair<String, String> saveFileWithDatePath(MultipartFile file, String directory) throws IOException {

        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String nowFormat = now.format(formatter);

        Path fullDirectory = Paths.get(directory, nowFormat).normalize();
        String uniqueFileName = generateUniqueFileName(file.getOriginalFilename());

        String fullPath = saveFile(file, fullDirectory.toString(), uniqueFileName);
        String accessPath = FileProperties.getAccessPrefix() + nowFormat + "/" + uniqueFileName;

        return Pair.of(fullPath, accessPath);
    }

    /**
     * 保存上传的文件到默认目录，按照日期自动创建子目录
     *
     * @param file 上传的文件
     * @return Pair 对象，Left 为物理完整路径，Right 为访问路径
     * @throws IOException 保存文件失败时抛出
     */
    public static Pair<String, String> saveFileWithDatePathDetail(MultipartFile file) throws IOException {
        return saveFileWithDatePath(file, DEFAULT_UPLOAD_PATH);
    }

    /**
     * 保存上传的文件到默认目录，按照日期自动创建子目录
     *
     * @param file 上传的文件
     * @return 访问路径
     * @throws IOException 保存文件失败时抛出
     */
    public static String saveFileWithDatePath(MultipartFile file) throws IOException {
        return saveFileWithDatePathDetail(file).getRight();
    }

    /**
     * 保存输入流到指定路径
     *
     * @param inputStream 输入流
     * @param targetPath  目标路径
     * @throws IOException 保存文件失败时抛出
     */
    public static void saveFile(InputStream inputStream, Path targetPath) throws IOException {
        ensureDirectoryExists(targetPath.getParent());
        Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     * @return 是否删除成功
     */
    public static boolean deleteFile(Path filePath) {
        try {
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径字符串
     * @return 是否删除成功
     */
    public static boolean deleteFile(String filePath) {
        return deleteFile(Paths.get(filePath));
    }

    /**
     * 格式化文件大小为可读格式
     *
     * @param size 文件大小（字节）
     * @return 格式化后的字符串（如：1.5 MB）
     */
    public static String formatFileSize(long size) {
        if (size < 0) {
            return "0 B";
        }

        final String[] units = {"B", "KB", "MB", "GB", "TB"};
        int unitIndex = 0;
        double fileSize = size;

        while (fileSize >= 1024 && unitIndex < units.length - 1) {
            fileSize /= 1024;
            unitIndex++;
        }

        return String.format("%.2f %s", fileSize, units[unitIndex]);
    }

    /**
     * 检查文件是否存在
     *
     * @param filePath 文件路径
     * @return 是否存在
     */
    public static boolean fileExists(Path filePath) {
        return filePath != null && Files.exists(filePath);
    }

    /**
     * 检查文件是否存在
     *
     * @param filePath 文件路径字符串
     * @return 是否存在
     */
    public static boolean fileExists(String filePath) {
        return filePath != null && Files.exists(Paths.get(filePath));
    }
}
