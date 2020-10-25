package com.wasylmowczan.matchers.pdf;

import de.redsix.pdfcompare.CompareResult;
import de.redsix.pdfcompare.PdfComparator;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.io.File;
import java.io.IOException;

public class PdfFileMatcher extends TypeSafeDiagnosingMatcher<File> {
    private final File expectedPdf;

    private PdfFileMatcher(File expectedPdf) {
        this.expectedPdf = expectedPdf;
    }

    public static PdfFileMatcher samePdfAs(File expectedPdf) {
        return new PdfFileMatcher(expectedPdf);
    }

    @Override
    protected boolean matchesSafely(File actualPdf, Description mismatchDescription) {
        try {
            CompareResult result = new PdfComparator<>(expectedPdf, actualPdf).compare();
            if (result.isNotEqual()) {
                mismatchDescription
                        .appendText(" files expected to be equal, but found differences ")
                        .appendValue(result.getDifferences());
                return false;
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("pdf file to be equal");
    }
}
