package com.wasylmowczan.matchers.excel.comparators;

import com.wasylmowczan.matchers.Comparator;
import com.wasylmowczan.matchers.txt.comparators.TextComparators;
import org.apache.poi.ss.usermodel.Cell;

class IgnoreTextComparator extends Comparator<Cell> {
    private final Comparator<String> ignoreTextComparator;

    IgnoreTextComparator(String textToIgnore) {
        this.ignoreTextComparator = TextComparators.ignoreText(textToIgnore);
    }

    @Override
    public boolean isApplicable(Cell expected) {
        String stringCellValue = expected.toString();
        return ignoreTextComparator.isApplicable(stringCellValue);
    }

    @Override
    protected boolean doCompare(Cell actual, Cell expected) {
        String actualValue = actual.toString();
        String expectedValue = expected.toString();

        return ignoreTextComparator.compare(actualValue, expectedValue);
    }

}
