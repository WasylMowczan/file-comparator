package com.wasylmowczan.matchers.excel;

import com.wasylmowczan.matchers.Comparator;
import com.wasylmowczan.matchers.CompositeComparator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.List;

import com.wasylmowczan.matchers.Comparator;
import com.wasylmowczan.matchers.CompositeComparator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.List;

import static bad.robot.excel.sheet.SheetIterable.sheetsOf;
import static com.wasylmowczan.matchers.excel.RowNumberMatcher.hasSameNumberOfRowAs;
import static com.wasylmowczan.matchers.excel.RowsMatcher.hasSameRowsAs;

class SheetsMatcher extends TypeSafeDiagnosingMatcher<Workbook> {

    private final Workbook expected;
    private final Comparator<Cell> cellComparator;

    SheetsMatcher(Workbook expected, List<Comparator<Cell>> cellComparators) {
        this.expected = expected;
        this.cellComparator = new CompositeComparator<>(cellComparators);
    }

    @Override
    protected boolean matchesSafely(Workbook actual, Description mismatch) {
        for (Sheet expectedSheet : sheetsOf(expected)) {
            Sheet actualSheet = actual.getSheet(expectedSheet.getSheetName());

            if (!hasSameNumberOfRowAs(expectedSheet).matchesSafely(actualSheet, mismatch))
                return false;

            if (!hasSameRowsAs(expectedSheet, cellComparator).matchesSafely(actualSheet, mismatch))
                return false;
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("equality on all sheets in workbook");
    }
}
