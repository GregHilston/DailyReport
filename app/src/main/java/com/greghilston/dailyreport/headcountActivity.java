package com.greghilston.dailyreport;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;


/**
 * Created by tcollins on 4/19/2015.
 */
public class HeadcountActivity extends Activity {

    CheckBox acmeBox;
    CheckBox researchBox;
    CheckBox destructionBox;



    protected int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.headcount_list);

        CheckBox acmeBox = (CheckBox) findViewById(R.id.checkBox9);
        CheckBox researchBox = (CheckBox) findViewById(R.id.checkBox10);

        counter = 0;
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

    }





}
