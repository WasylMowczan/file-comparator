package com.wasylmowczan.matchers.excel;

import com.wasylmowczan.matchers.Comparator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.ArrayList;
import java.util.List;

import static com.wasylmowczan.matchers.CompositeMatcher.allOf;
import static com.wasylmowczan.matchers.excel.RowInSheetMatcher.hasSameRow;

class RowsMatcher extends TypeSafeDiagnosingMatcher<Sheet> {

    private final Sheet expected;
    private final List<Matcher<Sheet>> rowsOnSheet;

    static RowsMatcher hasSameRowsAs(Sheet expected, Comparator<Cell> cellComparator) {
        return new RowsMatcher(expected, cellComparator);
    }

    private RowsMatcher(Sheet expected, Comparator<Cell> cellComparator) {
        this.expected = expected;
        this.rowsOnSheet = createRowMatchers(expected, cellComparator);
    }

    @Override
    protected boolean matchesSafely(Sheet actual, Description mismatch) {
        return allOf(rowsOnSheet).matchesSafely(actual, mismatch);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("equality on all rows in ").appendValue(expected.getSheetName());
    }

    private static List<Matcher<Sheet>> createRowMatchers(Sheet sheet, Comparator<Cell> cellComparator) {
        List<Matcher<Sheet>> matchers = new ArrayList<Matcher<Sheet>>();
        for (Row expected : sheet)
            matchers.add(hasSameRow(expected, cellComparator));
        return matchers;
    }

}

