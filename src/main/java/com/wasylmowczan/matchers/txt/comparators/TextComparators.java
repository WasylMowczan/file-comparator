package com.wasylmowczan.matchers.txt.comparators;

import com.wasylmowczan.matchers.Comparator;

public class TextComparators {
    public static Comparator<String> ignoreText(String textToIgnore) {
        return new IgnoreTextComparator(textToIgnore);
    }
}
