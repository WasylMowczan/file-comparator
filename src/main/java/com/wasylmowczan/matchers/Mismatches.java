package com.wasylmowczan.matchers;

import bad.robot.excel.matchers.MismatchDetector;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.lang.String.format;

class Mismatches<T> implements MismatchDetector<T> {

    private final List<Matcher<T>> mismatches = new ArrayList<Matcher<T>>();

    @Override
    public boolean discover(T actual, Iterable<Matcher<T>> matchers) {
        for (Matcher<T> matcher : matchers) {
            if (!matcher.matches(actual))
                mismatches.add(matcher);
        }
        return found();
    }

    void describeTo(Description description, T actual) {
        Iterator<Matcher<T>> iterator = mismatches.iterator();
        while (iterator.hasNext()) {
            iterator.next().describeMismatch(actual, description);
            if (iterator.hasNext())
                description.appendText(format(",\n%1$10s", ""));
        }
    }

    boolean found() {
        return !mismatches.isEmpty();
    }
}
