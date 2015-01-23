 package com.greghilston.dailyreport;

 import java.util.ArrayList;

 /***
 * Represents an entire day of observations
 */
public class Timeline{
    private ArrayList<Observation> observations = new ArrayList<Observation>();

    public Timeline() {
        observations.add(new Text("Arrived on site"));
    }
}
