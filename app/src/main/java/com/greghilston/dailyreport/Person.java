package com.greghilston.dailyreport;

/**
 * Represents a person on site
 */
public class Person extends Notable{
    private String name;
    private String jobTitle;
    private int hoursOnSite;

    /**
     * A person object is used by a report so a manager can list which employees of his were on site
     * @param name  name of person
     * @param jobTitle  job title of person
     * @param hoursOnSite  hours on site today
     */
    public Person(String name, String jobTitle, int hoursOnSite) {
        this.name = name;
        this.jobTitle = jobTitle;
        this.hoursOnSite = hoursOnSite;
    }

    /**
     * @return  name of the person
     */
    public String getName() {
        return name;
    }

    /**
     * @param name  name to set
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return  job title the person holds
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * @param jobTitle  title to set
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * @return  hours this person was on site today
     */
    public int getHoursOnSite() {
        return hoursOnSite;
    }

    /**
     * @param hoursOnSite  hours to set
     */
    public void setHoursOnSite(int hoursOnSite) {
        this.hoursOnSite = hoursOnSite;
    }
}
