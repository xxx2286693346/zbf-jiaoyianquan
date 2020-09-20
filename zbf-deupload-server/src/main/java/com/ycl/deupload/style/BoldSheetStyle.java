package com.ycl.deupload.style;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * 对标题 列名 统计结果 字体加粗
 */
public class BoldSheetStyle extends DefaultSheetStyle {
    @Override
    public CellStyle getTitleCellStyle(SXSSFWorkbook workbook) {
        if (titleCellStyle != null) {
            return titleCellStyle;
        }
        CellStyle style = super.getTitleCellStyle(workbook);
        workbook.getFontAt(style.getFontIndexAsInt()).setBold(true);
        return style;
    }

    @Override
    public CellStyle getColumnNamesCellStyle(SXSSFWorkbook workbook, int index) {
        if (columnNamesCellStyle != null) {
            return columnNamesCellStyle;
        }
        CellStyle style = super.getColumnNamesCellStyle(workbook, index);
        workbook.getFontAt(style.getFontIndexAsInt()).setBold(true);
        return style;
    }

    @Override
    public CellStyle getColumnCountCellStyle(SXSSFWorkbook workbook, int columnIndex, Object v) {
        if (columnCountCellStyle != null) {
            return columnCountCellStyle;
        }
        CellStyle style = super.getColumnCountCellStyle(workbook, columnIndex, v);
        workbook.getFontAt(style.getFontIndexAsInt()).setBold(true);
        return style;
    }
}
