package com.wasylmowczan.matchers.excel.comparators;

import com.wasylmowczan.matchers.Comparator;
import org.apache.poi.ss.usermodel.Cell;

public class ExcelComparators {
    public static Comparator<Cell> ignoreCell(long columnIdx, long rowIdx) {
        return new IgnoreCellComparator(columnIdx, rowIdx);
    }

    public static Comparator<Cell> ignoreCell(String cellCoordinates) {
        return IgnoreCellComparator.ignoreCell(cellCoordinates);
    }

    public static Comparator<Cell> ignoreText(String textToIgnore) {
        return new IgnoreTextComparator(textToIgnore);
    }
}
