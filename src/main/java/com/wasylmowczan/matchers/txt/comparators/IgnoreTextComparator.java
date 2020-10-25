package com.wasylmowczan.matchers.txt.comparators;

import com.wasylmowczan.matchers.Comparator;

import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;

public class IgnoreTextComparator extends Comparator<String> {
    private static final String REGEX_ESCAPE_START = "\\Q";
    private static final String REGEX_ESCAPE_END = "\\E";
    private final String textToIgnore;

    IgnoreTextComparator(String textToIgnore) {
        this.textToIgnore = textToIgnore;
    }

    @Override
    public boolean isApplicable(String expected) {
        return expected.contains(textToIgnore);
    }

    @Override
    public boolean doCompare(String actual, String expected) {
        checkArgument(expected.contains(textToIgnore), format("comparator is not applicable, expectedValue='%s'", expected));
        Pattern pattern = Pattern.compile(REGEX_ESCAPE_START + expected.replace(textToIgnore, format("%s.*%s", REGEX_ESCAPE_END, REGEX_ESCAPE_START)) + REGEX_ESCAPE_END);
        return pattern.matcher(actual).matches();
    }

}
