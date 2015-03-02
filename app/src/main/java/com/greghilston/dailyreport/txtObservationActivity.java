package com.greghilston.dailyreport;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Tyler on 3/1/2015.
 */
public class txtObservationActivity extends Activity {

    Button submit; //submit button
    EditText obs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.txt_observation);

        submit = (Button) findViewById(R.id.button2);
        obs = (EditText) findViewById(R.id.edtxtInput);

        submit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){
                Intent nxtScreen = new Intent(getApplicationContext(),MainActivity.class);
                nxtScreen.putExtra("extext",  obs.getText().toString());
                startActivity(nxtScreen);
                //finish();
                //

            }
        });
    }
}


