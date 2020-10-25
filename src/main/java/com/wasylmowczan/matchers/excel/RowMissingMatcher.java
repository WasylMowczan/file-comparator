package com.wasylmowczan.matchers.excel;

import org.apache.poi.ss.usermodel.Row;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static bad.robot.excel.PoiToExcelCoercions.asExcelRow;

class RowMissingMatcher extends TypeSafeDiagnosingMatcher<Row> {

    private final Row expected;

    static RowMissingMatcher rowIsPresent(Row expected) {
        return new RowMissingMatcher(expected);
    }

    private RowMissingMatcher(Row expected) {
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(Row actual, Description mismatch) {
        if (actual == null) {
            mismatch.appendText("row ").appendValue(asExcelRow(expected)).appendText(" is missing")
                    .appendText(" in sheet ").appendValue(expected.getSheet().getSheetName());
            return false;
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("row ").appendValue(asExcelRow(expected)).appendText(" to be present")
                .appendText(" in sheet ").appendValue(expected.getSheet().getSheetName());
    }
}

