package com.greghilston.dailyreport;

import android.app.Activity;
import android.content.Context;
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
    public Context context = this;
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

                //Retreive the current weather conditions
                String currently = FIOR.getCurrently().getValue("summary");
                float temperature = Float.parseFloat(FIOR.getCurrently().getValue("temperature"));
                float humid = Float.parseFloat(FIOR.getCurrently().getValue("humidity"));
                float pressure = Float.parseFloat(FIOR.getCurrently().getValue("pressure"));

                //Humidity is given in a float that ranges from 0-1 inclusive
                //Change to a percentage out of 100
                float relativeHumid = Float.parseFloat(Float.toString(humid));
                relativeHumid = relativeHumid*100;

                // TODO: Change what is returned? Or let the user choose
                //Add the weather observation
                String weatherResult = "Currently: " + currently + "\n"
                                    +"Temperature: " + temperature +"°F"+ "\n"
                                    +"Humidity: " + relativeHumid + "%" + "\n"
                                    +"Pressure: " + pressure + " millibar" + "\n";
                r.addObservation(new Weather(currently, temperature, relativeHumid, pressure));
                r.reportToGui(linearLayout, context);
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

                // TODO: Time
                int index = data.getIntExtra("index", 0);
                String t =  data.getStringExtra("text");

                Text text = (Text) r.getObservations().remove(index);
                text.setText(t);
                r.getObservations().add(index, text);
            }
            else if (resultCode == RESULT_CANCELED) {
                System.out.println("\t\tEdit Text Observation: Cancelled!");
            }
        }
        else if (requestCode == 3) {
            System.out.println("\t requestCode: 3");
            System.out.println("\t resultCode: " + resultCode);

            if(resultCode == RESULT_OK) {
                System.out.println("\t\tWeather Observation: changes made!");

                int index = data.getIntExtra("index", 0);
                String time = data.getStringExtra("time");
                String currently = data.getStringExtra("currently");
                String temperature = data.getStringExtra("temperature");
                String humidity = data.getStringExtra("humidity");
                String pressure = data.getStringExtra("pressure");

                Weather weather = (Weather) r.getObservations().remove(index);
                // TODO SET EVERYTHING
                r.getObservations().add(index, weather);
            }
            else if (resultCode == RESULT_CANCELED) {
                System.out.println("\t\tEdit Text Observation: Cancelled!");
            }
        }

        r.reportToGui(linearLayout, context);
    }
}
