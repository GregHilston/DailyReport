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
import com.greghilston.dailyreport.Forecast.io.v2.network.services.ForecastService;
import com.greghilston.dailyreport.Forecast.io.v2.transfer.LatLng;

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
