package com.greghilston.dailyreport;

/**
 * Represents a person on site
 */
public class Person extends Notable{
    private String name;
    private String jobTitle;
    private int hoursOnSite;

    public Person(String name, String jobTitle, int hoursOnSite) {
        this.name = name;
        this.jobTitle = jobTitle;
        this.hoursOnSite = hoursOnSite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
