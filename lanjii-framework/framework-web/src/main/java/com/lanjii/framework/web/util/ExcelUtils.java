package com.lanjii.framework.web.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.row.AbstractRowHeightStyleStrategy;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Excel工具类
 *
 * @author lanjii
 */
public class ExcelUtils {

    /**
     * 导出Excel
     *
     * @param <T>       数据类型
     * @param fileName  文件名
     * @param sheetName sheet名称
     * @param head      表头
     * @param data      数据
     */
    public static <T> void exportExcel(String fileName, String sheetName,
                                       Class<T> head, List<T> data) {
        HttpServletResponse resp = ServletUtils.getResponse();
        try {
            resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            resp.setCharacterEncoding("utf-8");
            fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            resp.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            EasyExcel.write(resp.getOutputStream(), head)
                    .sheet(sheetName)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()) // 自动列宽
                    .registerWriteHandler(createStyleStrategy()) // 样式配置
                    .registerWriteHandler(createRowHeightStrategy()) // 行高配置
                    .doWrite(data);
        } catch (IOException e) {
            throw new RuntimeException("导出Excel失败", e);
        }
    }

    /**
     * 导入Excel
     *
     * @param file 文件
     * @param head 表头类
     * @param <T>  数据类型
     * @return 数据列表
     */
    public static <T> List<T> importExcel(MultipartFile file, Class<T> head) {
        try {
            return EasyExcel.read(file.getInputStream())
                    .head(head)
                    .sheet()
                    .doReadSync();
        } catch (IOException e) {
            throw new RuntimeException("导入Excel失败", e);
        }
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

}