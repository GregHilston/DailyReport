package com.greghilston.dailyreport;

/***
 * Responsible for creating human readable documents
 */
public class DocumentMaster {
    private static DocumentMaster instance = new DocumentMaster();


    public DocumentMaster getInstance() {
        return instance;
    }


    /***
     * Creates a CSV based on the report
     */
    public void createCsvReport(Report r) {

    }


    /***
     * Creates a PDF based on the report
     */
    public void createPdfReport(Report r) {

    }
}
