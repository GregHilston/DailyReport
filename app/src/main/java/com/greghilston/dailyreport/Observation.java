 package com.greghilston.dailyreport;

 import java.io.Serializable;
 import android.content.Context;
 import android.location.Location;
 import android.location.LocationManager;

 import java.text.SimpleDateFormat;
 import java.util.Date;

 /***
 * Represents an observation being made by the user
 */
public abstract class Observation extends Notable implements Serializable {
     protected String time;

     public Observation() {
         time = generateTime();
     }

     /**
      * Gets the time in the form of h:mm:ss with a following am / pm
      * @return time stamp
      */
    public static String generateTime() {
        return new SimpleDateFormat("h:mm:ss a ").format(new Date());
    }

     /**
      * Returns the time stamp for when this observation was made
      * @return time
      */
     public String getTime() {
         return time;
     }


     /**
      * @param time  time to set for this observation
      */
     public void setTime(String time) {
        this.time = time;
     }
}
