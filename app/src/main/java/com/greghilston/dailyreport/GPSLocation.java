package com.greghilston.dailyreport;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;

/**
 * Created by ejo78 on 4/13/15.
 *
 * Based off video tutorial: https://www.youtube.com/watch?v=h7LUNCC0U1U
 */
public class GPSLocation extends Service implements LocationListener {
    private final Context context;

    boolean gpsEnabled = false;
    boolean networkEnabled = false;
    boolean canGetLocation = false;

    Location location;

    double latitude;
    double longitude;

    private static final long MIN_DIST_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BT_UPDATES = 1000 * 60 *1;

    protected LocationManager locationManager;

    public GPSLocation(Context context){
        this.context=context;
        getLocation();
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public Location getLocation() {
        try{
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

            gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if(!gpsEnabled && ! networkEnabled){

            }else{
                this.canGetLocation = true;

                if(networkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BT_UPDATES, MIN_DIST_CHANGE_FOR_UPDATES, this);


                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }

                    }
                }

                if(gpsEnabled){
                    if(location == null){
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                MIN_TIME_BT_UPDATES, MIN_DIST_CHANGE_FOR_UPDATES, this);
                    }
                    if(locationManager != null){
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                        if(location != null){
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }

                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return location;
    }

    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GPSLocation.this);
        }
    }

    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }
        return longitude;
    }

    public boolean canGetLocation(){
        return this.canGetLocation;
    }

    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setTitle("GPS");

        alertDialog.setMessage("GPS is not enabled. Do you want to go to the settings menu?");

        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
