package com.greghilston.dailyreport;

import java.util.ArrayList;

/**
 * Represents one of the many Projects the user may be working on
 */
public class Project {
	private String projectName;
	private ArrayList<Report> reports;

    public Project(String projectName) {
        this.projectName = projectName;
    }

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public ArrayList getReports() {
		return reports;
	}

	public void addReport(Report r) {
		reports.add(r);
	}

    public void removeReport(Report r) {
        reports.remove(r);
    }
}
