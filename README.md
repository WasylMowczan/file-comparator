# File comparator (XLSX, TXT, CSV, PDF)

## This is convenient file content comparator built with groovy. 
Steps: 
1. Create two folders and put files you want to compare.
2. Go to src/test/groovy/com/wasylmowczan/ReportComparisonTest.groovy and change REPORT_REFERENCE_DIR and REPORT_TEST_DIR.

```sh
 private static final File REPORT_REFERENCE_DIR = new File('E:\\report\\old')
 private static final File REPORT_TEST_DIR = new File('E:\\report\\new')
```

3. Change file name and set which cell/field should be ignored.

```sh
 where:
        fileNamePattern                          | comparators
        'file_\\d{4}-\\d{2}-\\d{2}.xls'          | [ignoreCell('A2'), ignoreCell('B3')]
```
4. Run JUnit test and check results.   
