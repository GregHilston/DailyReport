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
    private ImageButton safetyImageButton, issueImageButton, progressImageButton;
    private ImageView pictureTakenImageView;
    public static final int TAKE_PHOTO_CODE = 4; // TODO: Figure out what this does...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_observation);
        pictureTakenImageView = (ImageView) findViewById(R.id.imageView);

        String imagePath = getIntent().getStringExtra("imagePath");
        System.out.println("imagePath: " + imagePath);
        File destination = new File(imagePath);
        FileInputStream in = null;
        try {
            in = new FileInputStream(destination);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 10;
            Bitmap bmp = BitmapFactory.decodeStream(in, null, options);
            pictureTakenImageView.setImageBitmap(bmp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}