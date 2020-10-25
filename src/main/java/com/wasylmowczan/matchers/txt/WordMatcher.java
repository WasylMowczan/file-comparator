package com.wasylmowczan.matchers.txt;

import com.wasylmowczan.matchers.Comparator;
import com.wasylmowczan.matchers.CompositeComparator;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

class WordMatcher extends TypeSafeDiagnosingMatcher<String> {
    private final Comparator<String> comparator;
    private final String expected;

    WordMatcher(String expected, List<Comparator<String>> comparators) {
        checkArgument(comparators != null, "comparators passed to WordMatcher cannot be null");
        this.expected = expected;
        this.comparator = new CompositeComparator<>(comparators);
    }

    @Override
    protected boolean matchesSafely(String actual, Description mismatchDescription) {
        if (!comparator.compare(actual, expected)) {
            mismatchDescription
                    .appendText("\ngot     : ").appendValue(actual)
                    .appendText("\nexpected: ").appendValue(expected);
            return false;
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("word to be equal");
    }
}
