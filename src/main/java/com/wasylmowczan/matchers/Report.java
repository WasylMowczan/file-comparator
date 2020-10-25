package com.wasylmowczan.matchers;

import java.io.File;

import static java.lang.String.format;
import static java.util.Arrays.asList;

public class Report {
    private final File reportFile;
    private final ReportType type;

    public Report(File reportFile) {
        this.reportFile = reportFile;
        this.type = type(reportFile);
    }

    private static ReportType type(File f) {
        String fileName = f.getName().toLowerCase();
        if (fileName.endsWith(".xlsx") || fileName.endsWith(".xls")) {
            return ReportType.EXCEL;
        } else if (fileName.endsWith(".pdf")) {
            return ReportType.PDF;
        } else if (fileName.endsWith(".csv")) {
            return ReportType.CSV;
        } else if (fileName.endsWith(".txt")) {
            return ReportType.TXT;
        }
        throw new IllegalArgumentException(format("unknown type of report, file=%s, supportedTypes=%s", f.getAbsoluteFile(), asList(ReportType.values())));
    }

    public File getReportFile() {
        return reportFile;
    }

    public ReportType getType() {
        return type;
    }
}
