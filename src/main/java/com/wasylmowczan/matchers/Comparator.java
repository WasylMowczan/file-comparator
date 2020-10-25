package com.wasylmowczan.matchers;

public abstract class Comparator<T> {

    public abstract boolean isApplicable(T expected);

    public final boolean compare(T actual, T expected) {
        if (!isApplicable(expected)) {
            throw new IllegalArgumentException(String.format("comparator is not applicable for %s", expected.toString()));
        }
        return doCompare(actual, expected);
    }

    protected abstract boolean doCompare(T actual, T expected);
}
