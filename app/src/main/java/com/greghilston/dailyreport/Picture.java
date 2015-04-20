 package com.greghilston.dailyreport;

 /***
 * Represents a picture observation being made by the user
 */
public class Picture extends Observation {
     String picturePath = "";
     String pictureName = "";

     public Picture(String pictureName, String picturePath) {
         super();
         this.pictureName = pictureName;
         this.picturePath = picturePath;
     }

     public String getPictureName() {
         return pictureName;
     }

     public void setPictureName(String pictureName) {
         this.pictureName = pictureName;
     }

     public String getPicturePath() {
         return picturePath;
     }

     public void setPicturePath(String picturePath) {
         this.picturePath = picturePath;
     }
 }
