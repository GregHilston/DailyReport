 package com.greghilston.dailyreport;

 import java.io.Serializable;
 import java.text.ParseException;
 import java.text.SimpleDateFormat;
 import java.util.Calendar;
 import java.util.Date;

 /***
 * Represents an observation being made by the user
 */
public abstract class Observation extends Notable implements Serializable{
     protected Date date;

     public Observation() {
         date = generateTime();
     }

     /**
      * Gets the date in the form of h:mm:ss with a following am / pm
      * @return date stamp
      */
    public static Date generateTime() {
        return new Date();
    }

     /**
      * Returns the date stamp for when this observation was made
      * @return date
      */
     public Date getDate() {
         return date;
     }

     /**
      * Returns the time stamp for when this observation was made
      * @return  time
      */
     public String getTime() {
         Calendar calendar = Calendar.getInstance();
         calendar.setTime(date);
         int hours = calendar.get(Calendar.HOUR_OF_DAY);
         int minutes = calendar.get(Calendar.MINUTE);

         // TODO: GET AM / PM
         return hours + ":" + minutes;
     }

     /**
      * @param date  date to set for this observation
      */
     public void setDate(String date) {
         SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

         try {
             this.date = formatter.parse(date);
         } catch (ParseException e) {
             e.printStackTrace();
         }
     }
}
