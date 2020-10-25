package com.wasylmowczan.matchers.excel.comparators;

import com.wasylmowczan.matchers.Comparator;
import org.apache.poi.ss.usermodel.Cell;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;

class IgnoreCellComparator extends Comparator<Cell> {
    private static final int A_ASCII_CODE = 65;
    private static final Pattern COLUMN_COORDINATES_PATTERN = Pattern.compile("^(\\w)(\\d)$");
    private final long columnIdx;
    private final long rowIdx;

    IgnoreCellComparator(long columnIdx, long rowIdx) {
        this.columnIdx = columnIdx;
        this.rowIdx = rowIdx;
    }

    static IgnoreCellComparator ignoreCell(String cellCoordinate) {
        Matcher matcher = COLUMN_COORDINATES_PATTERN.matcher(cellCoordinate);
        if (matcher.matches()) {
            char[] columnLetterName = matcher.group(1).toUpperCase().toCharArray();
            int rowIdx = Integer.parseInt(matcher.group(2));
            checkArgument(rowIdx > 0, format("row coordinate must be higher then 0, coordinate=%s", cellCoordinate));
            int columnIdx = columnLetterName[0] - A_ASCII_CODE;

            return new IgnoreCellComparator(columnIdx, rowIdx - 1);
        } else {
            throw new IllegalArgumentException(format("cannot parse cell coordinates, coordinates='%s'", cellCoordinate));
        }
    }

    @Override
    public boolean isApplicable(Cell expected) {
        return expected.getColumnIndex() == columnIdx && expected.getRowIndex() == rowIdx;
    }

    @Override
    protected boolean doCompare(Cell actual, Cell expected) {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IgnoreCellComparator that = (IgnoreCellComparator) o;
        return columnIdx == that.columnIdx &&
                rowIdx == that.rowIdx;
    }

    @Override
    public int hashCode() {
        return Objects.hash(columnIdx, rowIdx);
    }
}
