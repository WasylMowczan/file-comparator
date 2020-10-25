package com.wasylmowczan.matchers.txt;

import com.google.common.collect.ImmutableList;
import com.wasylmowczan.matchers.Comparator;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;

class LineMatcher extends TypeSafeDiagnosingMatcher<String> {
    private final static List<String> WORD_SEPARATORS = ImmutableList.of(" ", "\t");
    private final LineLengthMatcher lineLengthMatcher;
    private final List<WordMatcher> wordMatchers;

    LineMatcher(String line, List<Comparator<String>> comparators) {
        List<String> words = lineToWords(new ArrayList<>(WORD_SEPARATORS), line);
        this.lineLengthMatcher = new LineLengthMatcher(line.length());
        this.wordMatchers = createWordMatchers(words, comparators);
    }

    private List<WordMatcher> createWordMatchers(List<String>words, List<Comparator<String>> comparators) {
        return words.stream().map(word -> new WordMatcher(word, comparators == null ? new ArrayList<>() : comparators))
                .collect(Collectors.toList());
    }

    @Override
    protected boolean matchesSafely(String line, Description mismatchDescription) {
        checkArgument(!line.contains("\n"), "a single line should be passed");
        List<String> words = lineToWords(new ArrayList<>(WORD_SEPARATORS), line);

        if (!lineLengthMatcher.matchesSafely(line.length(), mismatchDescription)) {
            mismatchDescription.appendText("\n\t actual line: ").appendValue(line);
            return false;
        }

        for (int idx = 0; idx < words.size(); idx++) {
            if (!wordMatchers.get(idx).matchesSafely(words.get(idx), mismatchDescription)) {
                mismatchDescription.appendValue(", actual line: ").appendValue(line);
                return false;
            }
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("line to be equal");
    }

    private static List<String> lineToWords(List<String> wordSeparators, String line) {
        List<String> words = new ArrayList<>();
        if (wordSeparators.isEmpty()) {
            return ImmutableList.of(line);
        }
        String[] lines = line.split(wordSeparators.get(0));
        wordSeparators.remove(0);
        for (String aLine : lines) {
            words.addAll(lineToWords(new ArrayList<>(wordSeparators), aLine));
        }

        return words;
    }
}
