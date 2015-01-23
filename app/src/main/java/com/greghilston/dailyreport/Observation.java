 package com.greghilston.dailyreport;

 import java.text.SimpleDateFormat;
 import java.util.Date;

 /***
 * Represents an observation being made by the user
 */
public abstract class Observation implements Notable{
    private String time;

     public Observation() {
         time = getTime();
     }

     /**
      * Gets the time in the form of h:mm:ss with a following am / pm
      * @return time stamp
      */
    public String getTime() {
        return new SimpleDateFormat("h:mm:ss a ").format(new Date());
    }
}
