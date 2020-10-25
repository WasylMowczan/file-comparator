package com.wasylmowczan.matchers;

import java.util.Objects;

public class ReportComparisonResult {
    private final boolean isScuccess;
    private final String errorMessage;

    private ReportComparisonResult(boolean isScuccess, String errorMessage) {
        this.isScuccess = isScuccess;
        this.errorMessage = errorMessage;
    }

    public static ReportComparisonResult success() {
        return new ReportComparisonResult(true, "");
    }

    public static ReportComparisonResult failure(String errorMessage) {
        return new ReportComparisonResult(false, errorMessage);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportComparisonResult that = (ReportComparisonResult) o;
        return isScuccess == that.isScuccess &&
                Objects.equals(errorMessage, that.errorMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isScuccess, errorMessage);
    }

    @Override
    public String toString() {
        return "ReportComparisonResult{" +
                "isScuccess=" + isScuccess +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
