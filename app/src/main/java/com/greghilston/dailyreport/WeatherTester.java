package com.greghilston.dailyreport;


import com.greghilston.dailyreport.ForecastIOLibrary.src.com.arcusweather.forecastio.ForecastIO;
import com.greghilston.dailyreport.ForecastIOLibrary.src.com.arcusweather.forecastio.ForecastIODataPoint;
import com.greghilston.dailyreport.ForecastIOLibrary.src.com.arcusweather.forecastio.ForecastIOResponse;

import java.util.HashMap;

/**
 * This class is used to test core weather functionality of the API we are using (ForecastIO).
 * This is being used as the GUI has not been setup yet.
 */
public class WeatherTester {
    static String API_KEY = "cbbd1fc614026e05d5429175cdfb0d10";
    static Double Lat = 43.1339;
    static Double Lang = 70.9264;

    
    public static void main(String args[]) {

        //Set the API key, Lat, and Lang
        ForecastIO forecast = new ForecastIO(API_KEY,Lat, Lang);

        //Set the request parameters
        //ability to set the units, exclude blocks, extend options and user agent for the request. This is not required.
        HashMap<String, String> requestParam = new HashMap<String, String>();
        requestParam.put("units", "us");
        requestParam.put("userAgent", "Custom User Agent 1.0");
        forecast.setRequestParams(requestParam);
        forecast.makeRequest();

        String responseString = forecast.getResponseString();
        ForecastIOResponse FIOR = new ForecastIOResponse(responseString);

        //The library provides an easy way to access values as strings and data points as a list.
        String currentSummary = FIOR.getValue("current-summary");
        String thirdHourlyTemperature = FIOR.getValue("hourly-2-temperature");
        String firstDailyIcon = FIOR.getValue("daily-0-icon");

        //alerts defaults to first alert if not given an index. (Usually there is only one alert).
        String alertDescription = FIOR.getValue("alerts-description");

        ForecastIODataPoint[] minutelyPoints = FIOR.getDataPoints("minutely");
        double thirtiethMinutePrecipitation = minutelyPoints[29].getValueAsDouble("precipitationIntensity");

        ForecastIODataPoint[] hourlyPoints = FIOR.getDataPoints("hourly");
        ForecastIODataPoint[] dailyPoints = FIOR.getDataPoints("daily");

        //you can also do it the hard way
        //String currentSummary = FIOR.getCurrently().getValue("summary");
        //String firstDailyIcon = FIOR.getDaily().getData[0].getValue("icon");

        System.out.print(currentSummary);
    }
}
