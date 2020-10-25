package com.wasylmowczan.matchers.excel;

import bad.robot.excel.matchers.CellType;
import com.wasylmowczan.matchers.Comparator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static bad.robot.excel.PoiToExcelCoercions.asExcelCoordinate;

public class CellInRowMatcher extends TypeSafeDiagnosingMatcher<Row> {

    private final Sheet sheet;
    private final Cell expected;
    private final int columnIndex;
    private final String coordinate;
    private final Comparator<Cell> comparator;

    private CellInRowMatcher(Sheet sheet, Cell expected, Comparator<Cell> comparator) {
        this.sheet = sheet;
        this.expected = expected;
        this.coordinate = asExcelCoordinate(expected);
        this.columnIndex = expected.getColumnIndex();
        this.comparator = comparator;
    }

    public static CellInRowMatcher hasSameCell(Sheet sheet, Cell expected, Comparator<Cell> comparator) {
        return new CellInRowMatcher(sheet, expected, comparator);
    }

    @Override
    protected boolean matchesSafely(Row row, Description mismatch) {
        Cell actual = row.getCell(columnIndex);

        if (!comparator.compare(actual, expected)) {
            mismatch.appendText("cell at ").appendValue(coordinate).appendText(" contained ").appendValue(CellType.adaptPoi(actual)).appendText(" expected ").appendValue(expected)
                    .appendText(" sheet ").appendValue(sheet.getSheetName());
            return false;
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("equality of cell ").appendValue(coordinate);
    }
}
