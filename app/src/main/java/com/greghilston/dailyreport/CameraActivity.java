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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import java.io.File;
import java.io.*;
import java.util.Date;

public class CameraActivity extends Activity {
    private ImageView pictureTakenImageView;
    public static final int TAKE_PHOTO_CODE = 4; // TODO: Figure out what this does...
    private Bitmap bmp;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // File based
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_observation);
        pictureTakenImageView = (ImageView) findViewById(R.id.imageView);

        imagePath = getIntent().getStringExtra("imagePath");
        System.out.println("imagePath: " + imagePath);

        BitmapFactory.Options options = null;
        options = new BitmapFactory.Options();
        options.inSampleSize = 2; // Fixes OutOfMemory Exceptions for working with Bitmaps
        bmp = BitmapFactory.decodeFile(imagePath, options);

        pictureTakenImageView.setImageBitmap(bmp);
    }
}