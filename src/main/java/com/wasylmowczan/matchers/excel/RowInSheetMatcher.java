package com.wasylmowczan.matchers.excel;

import com.wasylmowczan.matchers.Comparator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static bad.robot.excel.PoiToExcelCoercions.asExcelRow;
import static com.wasylmowczan.matchers.excel.CellNumberMatcher.hasSameNumberOfCellsAs;
import static com.wasylmowczan.matchers.excel.CellsMatcher.hasSameCellsAs;
import static com.wasylmowczan.matchers.excel.RowMissingMatcher.rowIsPresent;

class RowInSheetMatcher extends TypeSafeDiagnosingMatcher<Sheet> {

    private final Row expected;
    private final int rowIndex;
    private final Comparator<Cell> cellComparator;

    static RowInSheetMatcher hasSameRow(Row expected, Comparator<Cell> cellComparator) {
        return new RowInSheetMatcher(expected, cellComparator);
    }

    private RowInSheetMatcher(Row expected, Comparator<Cell> cellComparator) {
        this.cellComparator = cellComparator;
        this.expected = expected;
        this.rowIndex = expected.getRowNum();
    }

    @Override
    protected boolean matchesSafely(Sheet actualSheet, Description mismatch) {
        Row actual = actualSheet.getRow(rowIndex);

        if (!rowIsPresent(expected).matchesSafely(actual, mismatch))
            return false;

        if (!hasSameNumberOfCellsAs(expected).matchesSafely(actual, mismatch))
            return false;

        if (!hasSameCellsAs(expected, cellComparator).matchesSafely(actual, mismatch))
            return false;

        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("equality of row ").appendValue(asExcelRow(expected));
    }
}

