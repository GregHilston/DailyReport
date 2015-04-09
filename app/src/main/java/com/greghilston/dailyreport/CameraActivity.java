package com.greghilston.dailyreport;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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

public class CameraActivity extends Activity {
    private ImageButton safetyImageButton, issueImageButton, progressImageButton;
    private Uri capturedImageUri;
    private ImageView pictureTakenImageView;
    private Button takePictureButton;
    private final int TAKE_PHOTO_CODE = 1; // TODO: Figure out what this does...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_observation);

        pictureTakenImageView = (ImageView) findViewById(R.id.imageView);
        takePictureButton = (Button) findViewById(R.id.picButton);

        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runDefaultCameraTakingActivity();
            }
        });
    }

    /**
     * Opens the Android Device's default photo taking application
     * Note: Make prompt user to decide which application to use if no default has been set
     */
    public void runDefaultCameraTakingActivity(){
        File pictureFile = new File(getApplicationContext().getExternalFilesDir(null), "test.jpg");

        System.out.println("Path: " +  getApplicationContext().getExternalFilesDir(null) + "test.jpg");
        capturedImageUri = Uri.fromFile(pictureFile);

        System.out.println("Pre-Camera-Activity-For-Result");

        Intent takePictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri);
        startActivityForResult(takePictureIntent, TAKE_PHOTO_CODE);

        System.out.println("Post-Camera-Activity-For-Result");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.print("onActivityResult: CameraActivity");

        if (requestCode == TAKE_PHOTO_CODE) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), capturedImageUri);
                pictureTakenImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void unselectGuiElements(){
        System.out.println("Defaulting all GUI elements to grey");
        safetyImageButton.setImageResource(R.drawable.ic_launcher);
        issueImageButton.setImageResource(R.drawable.ic_launcher);
        progressImageButton.setImageResource(R.drawable.ic_launcher);
    }
}