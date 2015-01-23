package com.greghilston.dailyreport;

/**
 * Represents a person on site
 */
public class Person implements Notable{
    private String name;
    private String company;
    private String jobTitle;
    private int hoursOnSite;

    public Person(String name, String company, String jobTitle, int hoursOnSite) {
        this.name = name;
        this.company = company;
        this.jobTitle = jobTitle;
        this.hoursOnSite = hoursOnSite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public int getHoursOnSite() {
        return hoursOnSite;
    }

    public void setHoursOnSite(int hoursOnSite) {
        this.hoursOnSite = hoursOnSite;
    }

    @Override
    public String toString() {
        return name + " / " + company + " / " + jobTitle + " " + hoursOnSite + " hours today.";
    }
}
