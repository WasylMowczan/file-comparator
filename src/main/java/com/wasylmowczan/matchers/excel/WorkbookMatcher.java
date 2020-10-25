package com.wasylmowczan.matchers.excel;

import com.wasylmowczan.matchers.Comparator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.ArrayList;
import java.util.List;

import static com.wasylmowczan.matchers.excel.SheetMatcher.hasSameSheetsAs;

public class WorkbookMatcher extends TypeSafeDiagnosingMatcher<Workbook> {
    private final Workbook expected;
    private final List<Comparator<Cell>> cellComparators = new ArrayList<>();

    public static WorkbookMatcher sameWorkbook(Workbook expectedWorkbook) {
        return new WorkbookMatcher(expectedWorkbook);
    }

    private WorkbookMatcher(Workbook expected) {
        this.expected = expected;
    }

    public Matcher<Workbook> withCellComparators(List<Comparator<Cell>> comparators) {
        cellComparators.addAll(comparators);
        return this;
    }

    @Override
    protected boolean matchesSafely(Workbook actual, Description mismatch) {
        if (!hasSameSheetsAs(expected).matchesSafely(actual, mismatch))
            return false;

        if (!new SheetsMatcher(expected, cellComparators).matchesSafely(actual, mismatch))
            return false;

        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("entire workbook to be equal");
    }

}
