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
    Button cancelButton;
    EditText obs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.txt_observation);

        submit = (Button) findViewById(R.id.button2);
        cancelButton = (Button) findViewById(R.id.button);
        obs = (EditText) findViewById(R.id.edtxtInput);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent returnIntent = new Intent();
                setResult(RESULT_CANCELED, returnIntent);
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){
//                Intent nxtScreen = new Intent(getApplicationContext(),MainActivity.class);
//                nxtScreen.putExtra("extext",  obs.getText().toString());
//                startActivity(nxtScreen);
//                //finish();
//                //

                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", obs.getText().toString());
                setResult(RESULT_OK, returnIntent);
                finish();

            }
        });
    }
}


