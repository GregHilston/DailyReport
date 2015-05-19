package com.greghilston.dailyreport;

import java.util.ArrayList;

/**
 * Represents one of the many Projects the user may be working on
 */
public class Project {
	private String projectName;
    private String companyName;
	private ArrayList<Report> reports = new ArrayList<>();

    public Project(String projectName, String companyName) {
        this.projectName = projectName;
        this.companyName = companyName;
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

    /**
     * @return  this project's company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param company the company name this project is for
     */
    public void setCompanyName(String company) {
        this.companyName = company;
    }
}
