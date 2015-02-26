package com.greghilston.dailyreport;

import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents the interaction a user will have with the Android Application
 */
public class Tester {

    /**
     *
     */
    public static void testReport(String reportDescription, Report r) {
        System.out.println(reportDescription);
        System.out.println("----------------------------");

        // r.printReport();
    }

    public static void main(String args[]) {
        // Create the user's account
        Account account = new Account("Greg Hilston", "ACME Systems");

        // Create a project for the user
        Project project = new Project(account, "Construction");

        // Add the project to the user's account
        account.addProject(project);

        // Create a report with one person on site
        Report reportHandMade = new Report(project);

        // Add the report to the user's project
        project.addReport(reportHandMade);

        // Create and add a company to the report
        Company c = new Company("Patriots", 2);
        reportHandMade.addCompany(c);

        // Create and add a person to the report
        Person p = new Person("Tom Brady", "QB", 10);
        reportHandMade.addPerson(p);

        // Create and add a equipment to the report
        Equipment e = new Equipment("Deflated Football", 11);
        reportHandMade.addEquipment(e);

        Text t = new Text("Won Superbowl");
        reportHandMade.addObservation(t);

        testReport("Hand Made Report", reportHandMade); // Testing the hand made report object

        // Testing Report -> CSV
        DocumentMaster.getInstance().createCsv(reportHandMade);

//        // Testing Report -> XML (Works)
//        DocumentMaster.getInstance().createXml(reportHandMade);

//        // Testing the XML -> Report (Works)
//        Report reportFromXml = DocumentMaster.getInstance().createReportFromXml(reportHandMade.getFileName());
//        testReport("Report (from XML)", reportFromXml); // Testing the report object made from the XML file
//        reportFromXml.printReport();
    }
}
