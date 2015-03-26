package com.greghilston.dailyreport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greghilston.dailyreport.ForecastIOLibrary.src.com.arcusweather.forecastio.ForecastIO;
import com.greghilston.dailyreport.ForecastIOLibrary.src.com.arcusweather.forecastio.ForecastIODataPoint;
import com.greghilston.dailyreport.ForecastIOLibrary.src.com.arcusweather.forecastio.ForecastIOResponse;

import java.util.HashMap;

public class MainActivity extends Activity {
    Project project = new Project("Construction", "ACME");
    final Report r = new Report(project);
    LinearLayout linearLayout;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = (LinearLayout) findViewById(R.id.timeLine);
        textView = new TextView(getApplicationContext());

        r.reportToGui(linearLayout, textView);

        final Button cameraButton = (Button) findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent camScreen = new Intent(getApplicationContext(),CameraActivity.class);
                startActivity(camScreen);

            }
        });

        /**
         * For testing purposes, should just create an observation for the current weather of Durham
         */
        final Button weatherButton = (Button) findViewById(R.id.weatherButton);
        weatherButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { //Changing this to open up txt text screen
                System.out.println("Weather Observation Button Pressed!");

                String API_KEY = "cbbd1fc614026e05d5429175cdfb0d10";
                Double Lat = 43.1339;
                Double Lang = 70.9264;

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
                //String currently = FIOR.getValue("currently");
/*
                String thirdHourlyTemperature = FIOR.getValue("hourly-2-temperature");
                String firstDailyIcon = FIOR.getValue("daily-0-icon");

                //alerts defaults to first alert if not given an index. (Usually there is only one alert).
                String alertDescription = FIOR.getValue("alerts-description");

                ForecastIODataPoint[] minutelyPoints = FIOR.getDataPoints("minutely");
                double thirtiethMinutePrecipitation = minutelyPoints[29].getValueAsDouble("precipitationIntensity");

                ForecastIODataPoint[] hourlyPoints = FIOR.getDataPoints("hourly");
                ForecastIODataPoint[] dailyPoints = FIOR.getDataPoints("daily");


                //you can also do it the hard way

                //String firstDailyIcon = FIOR.getDaily().getData[0].getValue("icon");
                */

                //Retreive the current weather conditions
                String currently = FIOR.getCurrently().getValue("summary");
                String temp = FIOR.getCurrently().getValue("temperature");
                String humid = FIOR.getCurrently().getValue("humidity");
                String pressure = FIOR.getCurrently().getValue("pressure");

                //Humidity is given in a float that ranges from 0-1 inclusive
                //Change to a percentage out of 100
                float relativeHumid = Float.parseFloat(humid);
                relativeHumid = relativeHumid*100;

                System.out.println("Currently: " + currently + "\n");
                System.out.println("Temperature: " + temp +"°F"+ "\n");
                System.out.println("Humidity: " + relativeHumid + "%" + "\n");
                System.out.println("Pressure: " + pressure + " millibar" + "\n");
                System.out.print("Response String: " + responseString + "\n");

                // TODO: Change what is returned? Or let the user choose
                //Add the weather observation
                String weatherResult = "Currently: " + currently + "\n"
                                    +"Temperature: " + temp +"°F"+ "\n"
                                    +"Humidity: " + relativeHumid + "%" + "\n"
                                    +"Pressure: " + pressure + " millibar" + "\n";
                r.addObservation(new Text(weatherResult));
                r.reportToGui((LinearLayout) findViewById(R.id.timeLine)
                        , new TextView(getApplicationContext()));


            }
        });

        final Button noteButton = (Button) findViewById(R.id.noteButton);
        noteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent nextScreen = new Intent(getApplicationContext(), TextObservationActivity.class);
                startActivityForResult(nextScreen, 1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        else if (id == R.id.create_xml) {
            System.out.println("Generating a XML Document");
            DocumentMaster.getInstance().createXml(r, getApplicationContext().getFilesDir());
        }
        else if (id == R.id.create_csv) {
            System.out.println("Generating a CSV Document");
            DocumentMaster.getInstance().createCsv(r, getApplicationContext().getFilesDir());
        }


        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String result =  data.getStringExtra("result");

                if(result != "") { // Do not make an observation for an empty string
                    System.out.println("Text Observation Returned: " + result);
                    r.addObservation(new Text(result));
                    r.reportToGui((LinearLayout) findViewById(R.id.timeLine), new TextView(getApplicationContext()));
                }
            }
            if (resultCode == RESULT_CANCELED) {
                System.out.println("Text Observation Cancelled!");
            }
        }
    }
}
