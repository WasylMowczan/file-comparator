package com.wasylmowczan.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.Arrays;

public class CompositeMatcher<T> extends TypeSafeDiagnosingMatcher<T> {

    private final Iterable<Matcher<T>> matchers;

    @Factory
    public static <T> CompositeMatcher<T> allOf(Iterable<Matcher<T>> matchers) {
        return new CompositeMatcher<T>(matchers);
    }

    @Factory
    public static <T> TypeSafeDiagnosingMatcher<T> allOf(Matcher<T>... matchers) {
        return allOf(Arrays.asList(matchers));
    }

    private CompositeMatcher(Iterable<Matcher<T>> matchers) {
        this.matchers = matchers;
    }

    @Override
    public boolean matchesSafely(T actual, Description mismatch) {
        Mismatches<T> mismatches = new Mismatches<T>();
        if (mismatches.discover(actual, matchers))
            mismatches.describeTo(mismatch, actual);
        return !mismatches.found();
    }

    @Override
    public void describeTo(Description description) {
        description.appendList("", " ", "", matchers);
    }

}

