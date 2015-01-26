package com.greghilston.dailyreport;

/**
 * Represents the interaction a user will have with the Android Application
 */
public class Tester {

    public static void main(String args[]) {
        // Create the user's account
        Account account = new Account("Greg Hilston", "ACME Systems");

        // Create a project for the user
        Project project = new Project(account, "Construction");

        // Add the project to the user's account
        account.addProject(project);

        // Create a report with one person on site
        Report report = new Report(project, 1);

        // Add the report to the user's project
        project.addReport(report);

        // Create and add a company to the report
        Company c = new Company("Patriots", 2);
        report.addCompany(c);

        // Create and add a person to the report
        Person p = new Person("Tom Brady", "Patriots", "QB", 10);
        report.addPerson(p);

        // Create and add a equipment to the report
        Equipment e = new Equipment("Deflated Football", 11);
        report.addEquipment(e);

        report.generateReport();
    }
}
