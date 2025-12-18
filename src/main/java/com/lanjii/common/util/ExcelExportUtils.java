package com.lanjii.common.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.row.AbstractRowHeightStyleStrategy;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Excel导出工具类
 * 基于EasyExcel实现，使用@ExcelProperty注解配置导出字段
 *
 * @author lanjii
 */
@Slf4j
public class ExcelExportUtils {

    private ExcelExportUtils() {
        // 防止实例化
    }

    /**
     * 导出Excel到响应流
     *
     * @param response HTTP响应
     * @param data     导出数据
     * @param fileName 文件名（不包含扩展名）
     * @param <T>      数据类型
     */
    public static <T> void exportToResponse(HttpServletResponse response, List<T> data, String fileName) {
        exportToResponse(response, data, fileName, "Sheet1");
    }

    /**
     * 导出Excel到响应流（指定工作表名）
     *
     * @param response  HTTP响应
     * @param data      导出数据
     * @param fileName  文件名（不包含扩展名）
     * @param sheetName 工作表名
     * @param <T>       数据类型
     */
    public static <T> void exportToResponse(HttpServletResponse response, List<T> data, String fileName, String sheetName) {
        try {
            // 设置响应头
            setResponseHeaders(response, fileName);

            // 执行导出
            EasyExcel.write(response.getOutputStream(), getDataClass(data))
                    .registerWriteHandler(createStyleStrategy())
                    .registerWriteHandler(createRowHeightStrategy())
                    .sheet(sheetName)
                    .doWrite(data);

            log.info("Excel导出成功，文件名：{}，数据量：{}", fileName, data.size());
        } catch (Exception e) {
            log.error("Excel导出失败，文件名：{}", fileName, e);
            throw new RuntimeException("Excel导出失败", e);
        }
    }

    /**
     * 导出Excel到指定路径
     *
     * @param filePath 文件路径
     * @param data     导出数据
     * @param fileName 文件名（不包含扩展名）
     * @param <T>      数据类型
     */
    public static <T> void exportToFile(String filePath, List<T> data, String fileName) {
        exportToFile(filePath, data, fileName, "Sheet1");
    }

    /**
     * 导出Excel到指定路径（指定工作表名）
     *
     * @param filePath  文件路径
     * @param data      导出数据
     * @param fileName  文件名（不包含扩展名）
     * @param sheetName 工作表名
     * @param <T>       数据类型
     */
    public static <T> void exportToFile(String filePath, List<T> data, String fileName, String sheetName) {
        try {
            // 执行导出
            EasyExcel.write(filePath + "/" + fileName + ".xlsx", getDataClass(data))
                    .registerWriteHandler(createStyleStrategy())
                    .registerWriteHandler(createRowHeightStrategy())
                    .sheet(sheetName)
                    .doWrite(data);

            log.info("Excel导出成功，文件路径：{}/{}.xlsx，数据量：{}", filePath, fileName, data.size());
        } catch (Exception e) {
            log.error("Excel导出失败，文件路径：{}/{}", filePath, fileName, e);
            throw new RuntimeException("Excel导出失败", e);
        }
    }

    /**
     * 设置响应头
     */
    private static void setResponseHeaders(HttpServletResponse response, String fileName) throws IOException {
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFileName + ".xlsx");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
    }

    /**
     * 创建样式策略
     * 标题使用12号字体，内容左对齐，所有文字上下居中
     */
    private static HorizontalCellStyleStrategy createStyleStrategy() {
        // 表头样式
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 12); // 12号字体
        headWriteFont.setBold(true);
        headWriteCellStyle.setWriteFont(headWriteFont);
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        headWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 垂直居中

        // 内容样式
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 垂直居中

        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }

    /**
     * 创建行高策略
     * 设置行高为20
     */
    private static AbstractRowHeightStyleStrategy createRowHeightStrategy() {
        return new AbstractRowHeightStyleStrategy() {
            @Override
            protected void setHeadColumnHeight(org.apache.poi.ss.usermodel.Row row, int relativeRowIndex) {
                row.setHeightInPoints(20); // 表头行高20
            }

            @Override
            protected void setContentColumnHeight(org.apache.poi.ss.usermodel.Row row, int relativeRowIndex) {
                row.setHeightInPoints(20); // 内容行高20
            }
        };
    }

    /**
     * 获取数据类
     */
    @SuppressWarnings("unchecked")
    private static <T> Class<T> getDataClass(List<T> data) {
        if (data.isEmpty()) {
            throw new IllegalArgumentException("导出数据不能为空");
        }
        return (Class<T>) data.get(0).getClass();
    }

}
