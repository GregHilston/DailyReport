package com.greghilston.dailyreport;


import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;

public class LocationMaster {

    private static LocationMaster instance = new LocationMaster();
    private static LocationManager locationManager;
    private static Boolean initialized = false;
    private static Context con;

    public static LocationMaster getInstance() {
        if(!initialized) {
            throw new IllegalStateException("LocationMaster not initialized - run init(context)");
        }

        return instance;
    }

    /**
     * Initializes the LocationMaster based on the context giving
     * @param context  context given my an activity
     */
    public static void init(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        con = context;
        // android.location.Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        initialized = true;
    }

    /**
     * Gets the last known location's longitude
     * @return  longitude ot NULL
     */
    public Double getLongitude() {
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location == null)
        {
            Toast.makeText(con, "Location unavailable, using Durham",
                    Toast.LENGTH_LONG).show();
            Double Long = 70.9264;
            return Long;
        }
        return location.getLongitude();
    }

    /**
     * Gets the last known location's latitude
     * @return  latitude or NULL
     */
    public Double getLatitude() {
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location == null)
        {
            Toast.makeText(con, "Location unavailable, using Durham",
                    Toast.LENGTH_LONG).show();
            Double Lat = 43.1339;
            return Lat;
        }
        return location.getLatitude();
    }


}
