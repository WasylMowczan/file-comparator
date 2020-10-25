package com.wasylmowczan.matchers;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public class CompositeComparator<T> extends com.wasylmowczan.matchers.Comparator<T> {
    private final List<com.wasylmowczan.matchers.Comparator<T>> comparators;

    public CompositeComparator(List<com.wasylmowczan.matchers.Comparator<T>> comparators) {
        checkArgument(comparators != null, "comparators cannot be null");
        this.comparators = comparators;
    }

    @Override
    public boolean isApplicable(T expected) {
        return comparators.stream().anyMatch(comparator -> comparator.isApplicable(expected));
    }

    @Override
    public boolean doCompare(T actual, T expected) {
        com.wasylmowczan.matchers.Comparator<T> comparator = comparators.stream().filter(c -> c.isApplicable(expected)).findFirst().get();
        return comparator.compare(actual, expected);
    }
}
