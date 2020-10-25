package com.wasylmowczan.matchers.txt;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

class LineLengthMatcher extends TypeSafeDiagnosingMatcher<Integer> {
    private final int expectedLineLength;

    LineLengthMatcher(int expectedLineLength) {
        this.expectedLineLength = expectedLineLength;
    }

    @Override
    protected boolean matchesSafely(Integer actualLineLength, Description mismatchDescription) {
        if (expectedLineLength != actualLineLength) {
            mismatchDescription.appendText(" expected line length ").appendValue(expectedLineLength)
                    .appendText(" but actual ").appendValue(actualLineLength);
            return false;
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(" line length to be equal ").appendValue(expectedLineLength);
    }
}
