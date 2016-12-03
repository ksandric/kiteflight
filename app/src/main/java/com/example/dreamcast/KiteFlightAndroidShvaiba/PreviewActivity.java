package com.example.dreamcast.KiteFlightAndroidShvaiba;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PreviewActivity extends AppCompatActivity {

    TextView tvPText;
    Context mainContext;
    ImageView mainImage, avatarImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        //configuration ActionBar
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Preview Card");

        mainContext = this;

        tvPText = (TextView) findViewById(R.id.textViewMessageTextPreview);
        mainImage = (ImageView) findViewById(R.id.imageMainPreview);
        avatarImage = (ImageView) findViewById(R.id.avatarImagePreview);

        Intent intent = getIntent();
        tvPText.setText(intent.getStringExtra("text"));

        Picasso.with(PreviewActivity.this).load("file://" + mainContext.getExternalCacheDir().toString() + "/1.png").into(mainImage);
        Picasso.with(PreviewActivity.this).load("file://" + mainContext.getExternalCacheDir().toString() + "/2.png").into(avatarImage);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            case R.id.action_next:
                startActivity(new Intent(this, SelectReturnActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
