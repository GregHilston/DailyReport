package com.greghilston.dailyreport;

import com.greghilston.dailyreport.Forecast.io.v2.network.services.ForecastService;
import com.greghilston.dailyreport.Forecast.io.v2.transfer.LatLng;

/**
 * This class is used to test core weather functionality of the API we are using (ForecastIO).
 * This is being used as the GUI has not been setup yet.
 */
public class WeatherTester {
    static String API_KEY = "cbbd1fc614026e05d5429175cdfb0d10";
    
    public static void main(String args[]) {
        ForecastService.Builder forcastServiceBuilder = new ForecastService.Builder(API_KEY);
        LatLng.Builder latLngBuilder = new LatLng.Builder();
        
        // Durham, NH
        latLngBuilder.setLatitude(43.1339);
        latLngBuilder.setLongitude(70.9264);
        
        //Build the Lat & Lang
        LatLng ll = new LatLng(latLngBuilder);
        
        //Set the Lat and Lang 
        forcastServiceBuilder.setLatLng(ll);
        ForecastService.Request r = forcastServiceBuilder.build();
        
        //Building the request~ I think
        r.getUri();
    }
}
