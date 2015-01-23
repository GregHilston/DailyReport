 package com.greghilston.dailyreport;

 /***
 * Represents a picture observation being made by the user
 */
public class Picture extends Observation {
     // TODO: Store the image

     public Picture() {
         super();
     }

     @Override
     public String toString() {
         return "Picture's Note Field: " + note;
     }
 }
