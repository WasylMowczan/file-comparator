package com.wasylmowczan.matchers.excel.comparators;

import bad.robot.excel.matchers.CellType;
import com.wasylmowczan.matchers.Comparator;
import org.apache.poi.ss.usermodel.Cell;

public class DefaultCellComparator extends Comparator<Cell> {
    @Override
    public boolean isApplicable(Cell expected) {
        return true;
    }

    @Override
    protected boolean doCompare(Cell actual, Cell expected) {
        bad.robot.excel.cell.Cell expectedCell = CellType.adaptPoi(expected);
        bad.robot.excel.cell.Cell actualCell = CellType.adaptPoi(actual);

        return actualCell.equals(expectedCell);
    }
}
