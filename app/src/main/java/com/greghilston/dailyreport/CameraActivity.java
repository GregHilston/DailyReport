package com.greghilston.dailyreport;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.File;
import java.io.*;
import java.util.Date;

public class CameraActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private ImageView pictureTakenImageView;
    public static final int TAKE_PHOTO_CODE = 4; // TODO: Figure out what this does...
    private Bitmap bmp;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // File based
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_observation);
        pictureTakenImageView = (ImageView) findViewById(R.id.imageView);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        Spinner spinspin = (Spinner) findViewById(R.id.spinner);
        spinspin.setOnItemSelectedListener(this);



        imagePath = getIntent().getStringExtra("imagePath");

        BitmapFactory.Options options = null;
        options = new BitmapFactory.Options();
        options.inSampleSize = 2; // Fixes OutOfMemory Exceptions for working with Bitmaps
        bmp = BitmapFactory.decodeFile(imagePath, options);

        pictureTakenImageView.setImageBitmap(bmp);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

}