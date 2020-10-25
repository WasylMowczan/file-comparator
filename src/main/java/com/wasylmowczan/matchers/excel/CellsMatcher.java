package com.wasylmowczan.matchers.excel;

import com.wasylmowczan.matchers.Comparator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.ArrayList;
import java.util.List;

import static bad.robot.excel.PoiToExcelCoercions.asExcelRow;
import static com.wasylmowczan.matchers.CompositeMatcher.allOf;
import static com.wasylmowczan.matchers.excel.CellInRowMatcher.hasSameCell;

class CellsMatcher extends TypeSafeDiagnosingMatcher<Row> {

    private final Row expected;
    private final List<Matcher<Row>> cellsOnRow;

    static CellsMatcher hasSameCellsAs(Row expected, Comparator<Cell> cellComparator) {
        return new CellsMatcher(expected, cellComparator);
    }

    private CellsMatcher(Row expected, Comparator<Cell> comparator) {
        this.expected = expected;
        this.cellsOnRow = createCellMatchers(expected, comparator);
    }

    @Override
    protected boolean matchesSafely(Row actual, Description mismatch) {
        return allOf(cellsOnRow).matchesSafely(actual, mismatch);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("equality of all cells on row ").appendValue(asExcelRow(expected));
    }

    private static List<Matcher<Row>> createCellMatchers(Row row, Comparator<Cell> comparator) {
        List<Matcher<Row>> matchers = new ArrayList<Matcher<Row>>();
        for (Cell expected : row) {
            matchers.add(hasSameCell(row.getSheet(), expected, comparator));
        }
        return matchers;
    }

}

