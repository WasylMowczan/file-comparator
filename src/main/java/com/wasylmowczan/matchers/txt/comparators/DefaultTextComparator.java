package com.wasylmowczan.matchers.txt.comparators;

import com.wasylmowczan.matchers.Comparator;

public class DefaultTextComparator extends Comparator<String> {
    @Override
    public boolean isApplicable(String expected) {
        return true;
    }

    @Override
    protected boolean doCompare(String actual, String expected) {
        return actual.equals(expected);
    }
}
