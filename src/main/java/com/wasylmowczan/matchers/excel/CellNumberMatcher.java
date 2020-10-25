package com.wasylmowczan.matchers.excel;

import org.apache.poi.ss.usermodel.Row;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static bad.robot.excel.PoiToExcelCoercions.asExcelRow;

class CellNumberMatcher extends TypeSafeDiagnosingMatcher<Row> {

    private final Row expected;

    public static CellNumberMatcher hasSameNumberOfCellsAs(Row expected) {
        return new CellNumberMatcher(expected);
    }

    private CellNumberMatcher(Row expected) {
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(Row actual, Description mismatch) {
        if (numberOfCellsIn(expected) != numberOfCellsIn(actual)) {
            mismatch.appendText("got ")
                    .appendValue(numberOfCellsIn(actual))
                    .appendText(" cell(s) on row ")
                    .appendValue(asExcelRow(expected))
                    .appendText(" expected ")
                    .appendValue(numberOfCellsIn(expected))
                    .appendText(" sheet ")
                    .appendValue(expected.getSheet().getSheetName());
            return false;
        }
        if (numberOfPhysicalCellsIn(expected) != numberOfPhysicalCellsIn(actual)) {
            mismatch.appendText("got ")
                    .appendValue(numberOfPhysicalCellsIn(actual))
                    .appendText(" cell(s) containing value on row ")
                    .appendValue(asExcelRow(expected))
                    .appendText(" expected ")
                    .appendValue(numberOfPhysicalCellsIn(expected))
                    .appendText(" sheet ")
                    .appendValue(expected.getSheet().getSheetName());
            return false;
        }

        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(numberOfCellsIn(expected)).appendText(" cell(s) on row ").appendValue(asExcelRow(expected))
                .appendText(" sheet ").appendValue(expected.getSheet().getSheetName());
    }

    /** POI is zero-based */
    private static int numberOfCellsIn(Row row) {
        return row.getLastCellNum();
    }

    private static int numberOfPhysicalCellsIn(Row row) {
        return row.getPhysicalNumberOfCells();
    }
}
