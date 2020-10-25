package com.wasylmowczan.matchers.txt;

import com.wasylmowczan.matchers.Comparator;
import org.apache.commons.io.FileUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextFileMatcher extends TypeSafeDiagnosingMatcher<File> {
    private static final String EOL = "\n";
    private final String[] expectedLines;
    private final List<Comparator<String>> wordComparators = new ArrayList<>();

    public static TextFileMatcher sameFile(File expectedFile) {
        return new TextFileMatcher(expectedFile);
    }

    private TextFileMatcher(File expectedFile) {
        this.expectedLines = fileAsText(expectedFile).split(EOL);
    }

    public TextFileMatcher withWordComparators(List<Comparator<String>> comparators) {
        wordComparators.addAll(comparators);
        return this;
    }

    @Override
    protected boolean matchesSafely(File actual, Description mismatch) {
        String[] actualLines = fileAsText(actual).split(EOL);

        if (!LinesAmountMatcher.hasSameRowNumber(expectedLines.length).matchesSafely(actualLines.length, mismatch)) {
            return false;
        }

        for (int lineIdx = 0; lineIdx < actualLines.length; lineIdx++) {
            if (!new LineMatcher(expectedLines[lineIdx], wordComparators).matchesSafely(actualLines[lineIdx], mismatch)) {
                mismatch.appendText("\nin line number ").appendValue(lineIdx + 1);
                return false;
            }
        }
        return true;
    }

    private String fileAsText(File actual) {
        try {
            return FileUtils.readFileToString(actual, "UTF-8");
        } catch (IOException e) {
            throw  new RuntimeException(e);
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("entire file to be equal");
    }
}
