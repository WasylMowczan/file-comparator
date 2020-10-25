package com.wasylmowczan.matchers;

import bad.robot.excel.workbook.PoiWorkbookReader;
import com.wasylmowczan.matchers.excel.comparators.DefaultCellComparator;
import com.wasylmowczan.matchers.txt.comparators.DefaultTextComparator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.wasylmowczan.matchers.ReportComparisonResult.failure;
import static com.wasylmowczan.matchers.ReportComparisonResult.success;
import static com.wasylmowczan.matchers.ReportType.*;
import static com.wasylmowczan.matchers.excel.WorkbookMatcher.sameWorkbook;
import static com.wasylmowczan.matchers.pdf.PdfFileMatcher.samePdfAs;
import static com.wasylmowczan.matchers.txt.TextFileMatcher.sameFile;
import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReportComparator {
    private final List<Comparator<Cell>> xlsCellComparators;
    private final List<Comparator<String>> wordComparators;

    private ReportComparator(List<Comparator<Cell>> xlsCellComparators, List<Comparator<String>> wordComparators) {
        checkArgument(xlsCellComparators != null, "cell comparators cannot be null");
        checkArgument(wordComparators != null, "word comparators cannot be null");
        xlsCellComparators.add(new DefaultCellComparator());
        wordComparators.add(new DefaultTextComparator());
        this.wordComparators = wordComparators;
        this.xlsCellComparators = xlsCellComparators;
    }

    public ReportComparisonResult compare(Report actual, Report base) {
        try {
            if (EXCEL == base.getType()) {
                assertThat(workbook(actual.getReportFile()), sameWorkbook(workbook(base.getReportFile()))
                        .withCellComparators(xlsCellComparators));
            } else if (CSV == base.getType() || TXT == base.getType()) {
                assertThat(actual.getReportFile(), sameFile(base.getReportFile()).withWordComparators(wordComparators));
            } else if (PDF == base.getType()) {
                assertThat(actual.getReportFile(), samePdfAs(base.getReportFile()));
            } else {
                throw new IllegalStateException(format("unsupported report type %s", base.getType()));
            }
            return success();
        } catch (Exception e) {
            e.printStackTrace();
            return failure(e.getMessage());
        }
    }

    public static class Builder {
        private final List<Comparator<Cell>> xlsCellComparators = new ArrayList<>();
        private final List<Comparator<String>> wordComparators = new ArrayList<>();

        public static Builder aReportComparator() {
            return new Builder();
        }

        public Builder withCellComparators(List<Comparator<Cell>> comparators) {
            xlsCellComparators.addAll(comparators);
            return this;
        }

        public Builder withWordComparators(List<Comparator<String>> comparators) {
            wordComparators.addAll(comparators);
            return this;
        }

        public ReportComparator build() {
            return new ReportComparator(xlsCellComparators, wordComparators);
        }
    }

    private static Workbook workbook(File file) throws IOException {
        return new PoiWorkbookReader().read(file);
    }
}
