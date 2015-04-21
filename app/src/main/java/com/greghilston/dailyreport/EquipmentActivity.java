package com.greghilston.dailyreport;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


/**
 * Created by tcollins on 4/19/2015.
 */
public class EquipmentActivity extends Activity {


    public Context context = this;

    Button Submit;

    CheckBox cb1;
    CheckBox cb2;
    CheckBox cb3;
    CheckBox cb4;
    CheckBox cb5;
    CheckBox cb6;
    CheckBox cb7;

    CheckBox acmeBox;
    CheckBox researchBox;
    CheckBox destructionBox;




    protected int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.headcount_list);

        final CheckBox acmeBox = (CheckBox) findViewById(R.id.checkBox9);
        final CheckBox researchBox = (CheckBox) findViewById(R.id.checkBox10);
        final CheckBox destructionBox = (CheckBox) findViewById(R.id.checkBox11);

        final CheckBox cb1 = (CheckBox) findViewById(R.id.checkBox);
        final CheckBox cb2 = (CheckBox) findViewById(R.id.checkBox2);
        final CheckBox cb3 = (CheckBox) findViewById(R.id.checkBox3);
        final CheckBox cb4 = (CheckBox) findViewById(R.id.checkBox4);
        final CheckBox cb5 = (CheckBox) findViewById(R.id.checkBox5);
        final CheckBox cb6 = (CheckBox) findViewById(R.id.checkBox6);
        final CheckBox cb7 = (CheckBox) findViewById(R.id.checkBox7);

        final Button sub = (Button) findViewById(R.id.submitlist);

        counter = 0;



        acmeBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (acmeBox.isChecked()){
                    counter = counter + 1;
                }
                else {
                    counter = counter - 1;
                }
            }
        });
        researchBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (researchBox.isChecked()){
                    counter = counter + 1;
                }
                else {
                    counter = counter - 1;
                }
            }
        });


        destructionBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Toast.makeText(context, String.valueOf(counter),
                        Toast.LENGTH_SHORT).show();
            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                counter = 0;

                if(cb1.isChecked())
                    counter++; // you can save this as checked somewhere
                if(cb2.isChecked())
                    counter++; // you can save this as checked somewhere
                if(cb3.isChecked())
                    counter++; // you can save this as checked somewhere
                if(cb4.isChecked())
                    counter++; // you can save this as checked somewhere
                if(cb5.isChecked())
                    counter++; // you can save this as checked somewhere
                if(cb6.isChecked())
                    counter++; // you can save this as checked somewhere
                if(cb7.isChecked())
                    counter++; // you can save this as checked somewhere


                Toast.makeText(context, String.valueOf(counter),
                        Toast.LENGTH_SHORT).show();
            }
        });



    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.checkBox9:
                if (checked){
                    Toast.makeText(context, "Checked ACME",
                            Toast.LENGTH_LONG).show();
                }
                else
                    // Nothing
                    break;
            case R.id.checkBox10:
                if (checked){
                    Toast.makeText(context, "Checked Research",
                            Toast.LENGTH_LONG).show();
                }
                else
                    // Nothing
                    break;
        }


    }

}
