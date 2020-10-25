package com.wasylmowczan.matchers.excel;

import org.apache.poi.ss.usermodel.Workbook;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static bad.robot.excel.matchers.SheetNameMatcher.containsSameNamedSheetsAs;
import static bad.robot.excel.matchers.SheetNumberMatcher.hasSameNumberOfSheetsAs;
import static com.wasylmowczan.matchers.CompositeMatcher.allOf;

class SheetMatcher extends TypeSafeDiagnosingMatcher<Workbook> {

    private final Matcher<Workbook> matchers;

    static SheetMatcher hasSameSheetsAs(Workbook expected) {
        return new SheetMatcher(expected);
    }

    private SheetMatcher(Workbook expected) {
        Matcher<Workbook> numberOfSheets = hasSameNumberOfSheetsAs(expected);
        Matcher<Workbook> namesOfSheets = containsSameNamedSheetsAs(expected);
        this.matchers = allOf(numberOfSheets, namesOfSheets);
    }

    @Override
    protected boolean matchesSafely(Workbook actual, Description mismatch) {
        boolean match = matchers.matches(actual);
        if (!match)
            matchers.describeMismatch(actual, mismatch);
        return match;
    }

    @Override
    public void describeTo(Description description) {
        matchers.describeTo(description);
    }
}

