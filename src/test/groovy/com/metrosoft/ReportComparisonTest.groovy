package com.metrosoft

import com.wasylmowczan.matchers.Comparator
import com.wasylmowczan.matchers.Report
import com.wasylmowczan.matchers.ReportComparator
import com.wasylmowczan.matchers.txt.comparators.IgnoreTextComparator
import com.wasylmowczan.matchers.txt.comparators.TextComparators
import org.apache.poi.ss.usermodel.Cell
import spock.lang.Specification
import spock.lang.Unroll

import java.util.regex.Pattern

import static com.wasylmowczan.matchers.ReportComparator.Builder.aReportComparator
import static com.wasylmowczan.matchers.ReportComparisonResult.success
import static com.wasylmowczan.matchers.excel.comparators.ExcelComparators.ignoreText

class ReportComparisonTest extends Specification {
    private static final File REPORT_REFERENCE_DIR = new File('E:\\report\\old')
    private static final File REPORT_TEST_DIR = new File('E:\\report\\new')

    @Unroll
    def 'xls reports should be the same, reportNamePattern=#fileNamePattern'(String fileNamePattern, List<Comparator<Cell>> comparators) {
        given:
        ReportComparator comparator = aReportComparator().withCellComparators(comparators).build()
        Report baseReport = new Report(find(REPORT_REFERENCE_DIR, fileNamePattern))
        Report actualReport = new Report(find(REPORT_TEST_DIR, fileNamePattern))

        expect:
        comparator.compare(actualReport, baseReport) == success()

        where:
        fileNamePattern                          | comparators
        'file_\\d{4}-\\d{2}-\\d{2}.xls'          | [ignoreCell('A2'), ignoreCell('B3')]
    }

    @Unroll
    def 'txt/csv reports should be the same, reportNamePattern=#fileNamePattern'(String fileNamePattern, List<IgnoreTextComparator> comparators) {
        given:
        ReportComparator comparator = aReportComparator().withWordComparators(comparators).build()
        Report baseReport = new Report(find(REPORT_REFERENCE_DIR, fileNamePattern))
        Report actualReport = new Report(find(REPORT_TEST_DIR, fileNamePattern))

        expect:
        comparator.compare(actualReport, baseReport) == success()

        where:
        fileNamePattern                            | comparators
        'file.txt'                                 | [ignoreText('String')]
        'file.csv'                                 | []
    }

    @Unroll
    def 'pdf reports should be the same, reportNamePattern=#fileNamePattern'(String fileNamePattern, List<IgnoreTextComparator> comparators) {
        given:
        ReportComparator comparator = aReportComparator().build()
        Report baseReport = new Report(find(REPORT_REFERENCE_DIR, fileNamePattern))
        Report actualReport = new Report(find(REPORT_TEST_DIR, fileNamePattern))

        expect:
        comparator.compare(actualReport, baseReport) == success()

        where:
        fileNamePattern                          | comparators
        'file.pdf'                               | []
    }

    private static File find(File dir, String fileNamePattern) {
        def files = dir.listFiles().findAll { file ->
            file.name ==~ Pattern.compile(fileNamePattern)
        }
        if (files == null) {
            throw new IllegalStateException("cannot find report file, dir=${dir.absolutePath}, pattern=${fileNamePattern}")
        } else if (files.size() != 1) {
            throw new IllegalStateException("expected to find only one file, dir=${dir.absolutePath}, pattern=${fileNamePattern}, filesFound=${files*.name}")
        }
        return files[0]
    }
}
