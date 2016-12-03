package com.example.dreamcast.KiteFlightAndroidShvaiba;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

public class MainPicture extends AppCompatActivity {

    private final int CAMERA_RESULT = 0;
    private ImageView mImageView, mainImage, avatarImage, arrowImage, ivGoneImage;
    private Button    buttonUseThisOne;
    private TextView  title, tvImage;

    Context mainContext;

    int togle = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_picture);

        //configuration ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mainContext = this;

        ivGoneImage      = (ImageView) findViewById(R.id.goneImage);
        mImageView       = (ImageView) findViewById(R.id.no_photo);
        mainImage        = (ImageView) findViewById(R.id.imageMain);
        avatarImage      = (ImageView) findViewById(R.id.avatarImage);
        arrowImage       = (ImageView) findViewById(R.id.imageArrow);
        buttonUseThisOne = (Button)    findViewById(R.id.buttonUseThisOne);
        title            = (TextView)  findViewById(R.id.textViewTitleSetPic);
        tvImage          = (TextView)  findViewById(R.id.textViewIMage);

        //Change Screen
        arrowImage.setVisibility(View.GONE);
        buttonUseThisOne.setClickable(false);
        buttonUseThisOne.setAlpha((float) 0.5);


    }

    public void onClickNewPhoto(View v) {
        CropImage.startPickImageActivity(this);
        buttonUseThisOne.setClickable(true);
        buttonUseThisOne.setAlpha((float) 1);
    }

    public void onClickUseThis(View v) {

        if(togle == 1){
            avatarImage.setImageDrawable(mImageView.getDrawable());


            try {
                File file = new File(mainContext.getExternalCacheDir().toString(), "1.png");
                OutputStream fOut = new FileOutputStream(file);

                ivGoneImage.buildDrawingCache();
                Bitmap bitmap = ivGoneImage.getDrawingCache();
                bitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut);
                fOut.flush();
                fOut.close();
                //Toast.makeText(mainContext, mainContext.getExternalCacheDir().toString(), Toast.LENGTH_LONG).show();
            }
            catch (Exception e)
            {
                Toast.makeText(mainContext, "save error", Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent(this, WriteMessageActivity.class);
            startActivity(intent);
        }
        if(togle == 0){
            mainImage.setImageDrawable(mImageView.getDrawable());

            try {
                File file = new File(mainContext.getExternalCacheDir().toString(), "2.png");
                OutputStream fOut = new FileOutputStream(file);

                ivGoneImage.buildDrawingCache();
                Bitmap bitmap = ivGoneImage.getDrawingCache();
                bitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut);
                fOut.flush();
                fOut.close();
                //Toast.makeText(mainContext, mainContext.getExternalCacheDir().toString(), Toast.LENGTH_LONG).show();
            }
            catch (Exception e)
            {
                Toast.makeText(mainContext, "save error", Toast.LENGTH_SHORT).show();
            }


            //Change Screen
            arrowImage.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle("Set Profile Picture");
            title.setText("Select A Second Image For Your Postcard");
            tvImage.setVisibility(View.INVISIBLE);

            togle = 1;
        }

       buttonUseThisOne.setClickable(false);
       buttonUseThisOne.setAlpha((float) 0.5);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                mImageView.setImageURI(resultUri);
                ivGoneImage.setImageURI(resultUri);
                Toast.makeText(mainContext, resultUri.toString(), Toast.LENGTH_LONG).show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            startCropImageActivity(imageUri);

        }


    }

    @Override
    public void onBackPressed() {
        togle = 0;

        //Change Screen
        arrowImage.setVisibility(View.GONE);
        getSupportActionBar().setTitle("Main Picture");
        title.setText("Select A Main Image For Your Postcard");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            case R.id.action_next:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setBorderLineColor(Color.rgb(33,150,243))
                .setAspectRatio(1, 1)
                .start(this);
    }
}
