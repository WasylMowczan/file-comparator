package com.wasylmowczan.matchers.excel;

import org.apache.poi.ss.usermodel.Sheet;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

class RowNumberMatcher extends TypeSafeDiagnosingMatcher<Sheet> {
    private final Sheet expected;

    static RowNumberMatcher hasSameNumberOfRowAs(Sheet expected) {
        return new RowNumberMatcher(expected);
    }

    private RowNumberMatcher(Sheet expected) {
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(Sheet actual, Description mismatch) {
        if (expected.getLastRowNum() != actual.getLastRowNum()) {
            mismatch.appendText("got ")
                    .appendValue(numberOfRowsIn(actual))
                    .appendText(" row(s) in sheet ")
                    .appendValue(actual.getSheetName())
                    .appendText(" expected ")
                    .appendValue(numberOfRowsIn(expected));
            return false;
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(numberOfRowsIn(expected)).appendText(" row(s) in sheet ").appendValue(expected.getSheetName());
    }

    /* POI is zero-based */
    private static int numberOfRowsIn(Sheet sheet) {
        return sheet.getLastRowNum() + 1;
    }
}
