package com.wasylmowczan.matchers.txt;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

class LinesAmountMatcher extends TypeSafeDiagnosingMatcher<Integer> {
    private final int expectedRowNum;

    private LinesAmountMatcher(int expectedRowNum) {
        this.expectedRowNum = expectedRowNum;
    }

    static LinesAmountMatcher hasSameRowNumber(int expectedRowNumber) {
        return new LinesAmountMatcher(expectedRowNumber);
    }

    @Override
    protected boolean matchesSafely(Integer actualLinesCount, Description mismatch) {
        if (actualLinesCount != expectedRowNum) {
            mismatch.appendText("\ngot     : ").appendValue(actualLinesCount).appendText(" rows")
                    .appendText("\nexpected: ").appendValue(expectedRowNum);
            return false;
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(expectedRowNum).appendText(" rows");
    }
}
