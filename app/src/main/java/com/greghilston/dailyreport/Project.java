package com.greghilston.dailyreport;

import java.util.ArrayList;

/**
 * Represents one of the many Projects the user may be working on
 */
public class Project {
    private Account account; // The account this project belongs to
	private String projectName;
	private ArrayList<Report> reports = new ArrayList<>();

    public Project(Account account, String projectName) {
        this.account = account;
        this.projectName = projectName;
    }

    /**
     * @return  this project's account
     */
    public Account getAccount() {
        return account;
    }

    /**
     * @return  this project's name
     */
	public String getProjectName() {
		return projectName;
	}

    /**
     * @param projectName   project name to set
     */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

    /**
     * @return  all of this project's reports
     */
	public ArrayList getReports() {
		return reports;
	}

    /**
     * @param r a report to add to this project
     */
	public void addReport(Report r) {
		reports.add(r);
	}

    /**
     * @param r a report to remove from this project
     */
    public void removeReport(Report r) {
        reports.remove(r);
    }
}
