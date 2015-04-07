package com.greghilston.dailyreport;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CameraActivity extends Activity {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;
    public static final int MEDIA_TYPE_IMAGE = 1;
    static Uri capturedImageUri=null;


    ImageView picture;
    Button snapButton;
    ImageButton sel1, sel2, sel3, sel4, sel5, sel6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_observation);

        picture = (ImageView) findViewById(R.id.imageView);
        snapButton = (Button) findViewById(R.id.picButton);

        snapButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                open();
            }
            //Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
            //fileUri = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            //i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            //startActivityForResult(i, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);


            //finish() ????? // TODO: Tyler - We should figure this out - Greg
            // I think if we just finish this it will default back to Timeline screen

        });
    }


    public void open (){
        //Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivityForResult(intent, 0);


        Calendar cal = Calendar.getInstance();
        File file = new File(Environment.getExternalStorageDirectory(),  (cal.getTimeInMillis()+".jpg"));
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else{
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        capturedImageUri = Uri.fromFile(file);
        Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        i.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri);
        startActivityForResult(i, 2);




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        // super.onActivityResult(requestCode, resultCode, data);
        // Bitmap bp = (Bitmap) data.getExtras().get("data");
        // picture.setImageBitmap(bp);

        if (requestCode == 2) {
            //Bitmap photo = (Bitmap) data.getExtras().get("data");
            //imageView.setImageBitmap(photo);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap( getApplicationContext().getContentResolver(),  capturedImageUri);
                picture.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


    }





/*

        //Type Button code, this handles if the type is selected
        sel1 = (ImageButton) findViewById(R.id.sel1);
        sel2 = (ImageButton) findViewById(R.id.sel2);
        sel3 = (ImageButton) findViewById(R.id.sel3);
        sel4 = (ImageButton) findViewById(R.id.sel4);
        sel5 = (ImageButton) findViewById(R.id.sel5);
        sel6 = (ImageButton) findViewById(R.id.sel6);
        unselectGuiElements();

        sel1.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                unselectGuiElements();
                if (event.getAction() == MotionEvent.ACTION_DOWN ) {
                    sel1.setImageResource(R.drawable.newforma_logo);
                    // Need to create the obsSelected image mirrors for each sel button
                }
                return false;
            }
        });

        sel2.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                unselectGuiElements();
                if (event.getAction() == MotionEvent.ACTION_DOWN ) {
                    sel2.setImageResource(R.drawable.newforma_logo);
                    //GG
                    // Need to create the obsSelected image mirrors for each sel button
                }
                return false;
            }
        });
    }
*/
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                Toast.makeText(this, "Image saved to:\n" +
                        data.getData(), Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }
        }
    }*/
/*
    *//** Create a file Uri for saving an image or video *//*
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    *//** Create a File for saving an image or video *//*
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                System.out.println("MyCameraApp failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        }
        else {
            return null; // TODO: Tyler, why is this here? -Greg
        }

        return mediaFile;
    }

    */

    public void unselectGuiElements(){

        System.out.println("Defaulting all GUI elements to grey");
        sel1.setImageResource(R.drawable.ic_launcher);
        sel2.setImageResource(R.drawable.ic_launcher);
        sel3.setImageResource(R.drawable.ic_launcher);


    }

/*
    @Override
    private void takePicIntent(){
        Intent tpIntent = new Intent(MediaStore.CAP_Req);
        if (tpIntent.resolveActivity(getPackageManager())!= null){
            startActivityForResult(tpIntent, CAP_Req);
        }
    } */
}