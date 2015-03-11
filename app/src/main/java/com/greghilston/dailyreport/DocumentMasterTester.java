package com.greghilston.dailyreport;

import android.content.Intent;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents the interaction a user will have with the Android Application.
 * This class is used to test core backend functionality before the GUI of the application has been
 * developed
 */
public class DocumentMasterTester {
    /**
     *
     */
    public static void testCreateXmlFromHandmade() {
        // Create a project for the user
        Project project = new Project("Construction", "ACME");

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

        DocumentMaster.getInstance().createXml(reportHandMade, new File("app/src/main/java/com/greghilston/dailyreport/Reports/"));
    }

    public static void testCreateCsvFromHandmade() {
        // Create a project for the user
        Project project = new Project("Construction", "ACME");

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

        DocumentMaster.getInstance().createCsv(reportHandMade, new File("app/src/main/java/com/greghilston/dailyreport/Reports/"));
    }

    public static void testCreateReportFromXml() {
        // Create a project for the user
        Project project = new Project("Construction", "ACME");

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

        DocumentMaster.getInstance().createXml(reportHandMade, new File("app/src/main/java/com/greghilston/dailyreport/Reports/"));

        // Testing the XML -> Report (Works)
        Report reportFromXml = DocumentMaster.getInstance().createReportFromXml(reportHandMade.getFileName());
        reportFromXml.printReport();
    }


    public static void main(String args[]) {
        // Run your tests here
        testCreateReportFromXml();
    }
}
