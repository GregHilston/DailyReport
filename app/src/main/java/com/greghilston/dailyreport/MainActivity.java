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
import com.greghilston.dailyreport.ForecastIOLibrary.src.com.arcusweather.forecastio.ForecastIOResponse;
import java.util.HashMap;

public class MainActivity extends Activity {
    Project project = new Project("Construction", "ACME");
    final Report r = new Report(project);
    public LinearLayout linearLayout;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = (LinearLayout) findViewById(R.id.timeLine);
        textView = new TextView(getApplicationContext());

        r.reportToGui(linearLayout, this);

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

                /*
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
                */

                System.out.print("Response String: " + responseString);
                // TODO: Finish completing the request
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("onActivityResult");

        if (requestCode == 1) {
            System.out.println("\t requestCode: 1");
            System.out.println("\t resultCode: " + resultCode);

            if(resultCode == RESULT_OK) {
                String result =  data.getStringExtra("result");

                if(result != "") { // Do not make an observation for an empty string
                    System.out.println("\t\tText Observation Returned: " + result);
                    r.addObservation(new Text(result));
                    r.reportToGui((LinearLayout) findViewById(R.id.timeLine), this);
                }
            }
            else if (resultCode == RESULT_CANCELED) {
                System.out.println("\t\tText Observation: Cancelled!");
            }
        }
        else if (requestCode == 2) {
            System.out.println("\t requestCode: 2");
            System.out.println("\t resultCode: " + resultCode);

            if(resultCode == RESULT_OK) {
                System.out.println("\t\tEdit Text Observation: changes made!");

                String t =  data.getStringExtra("text");
                int index = data.getIntExtra("index", 0);

                Text text = (Text) r.getObservations().remove(index);
                text.setText(t);
                r.getObservations().add(index, text);
            }
            else if (resultCode == RESULT_CANCELED) {
                System.out.println("\t\tEdit Text Observation: Cancelled!");
            }
        }
    }
}
