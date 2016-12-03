package com.example.dreamcast.KiteFlightAndroidShvaiba;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class PreviewActivity2 extends AppCompatActivity {

    TextView tvPText;
    Context mainContext;
    ImageView mainImage, avatarImage, ivSize;
    FrameLayout flFrame, flFrameTop;
    Button bSend, bEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview2);

        //configuration ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Preview Card");

        mainContext = this;

        tvPText = (TextView) findViewById(R.id.textViewMessageTextPreview);
        mainImage = (ImageView) findViewById(R.id.imageMainPreview);
        avatarImage = (ImageView) findViewById(R.id.avatarImagePreview);
        ivSize =(ImageView) findViewById(R.id.imageButtonSize);
        flFrame = (FrameLayout) findViewById(R.id.Frame03);
        flFrameTop = (FrameLayout) findViewById(R.id.frameLayoutPreviewTop);
        bSend = (Button) findViewById(R.id.buttonPreviewSend);
        bEdit = (Button) findViewById(R.id.buttonPreviewEdit);

        Intent iintent = getIntent();
        tvPText.setText(iintent.getStringExtra("text"));

        Picasso.with(mainContext).load("file://" + mainContext.getExternalCacheDir().toString() + "/1.png").into(mainImage);
        Picasso.with(mainContext).load("file://" + mainContext.getExternalCacheDir().toString() + "/2.png").into(avatarImage);

        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mainContext, "Sending...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PreviewActivity2.this, MainMenu.class);
                startActivity(intent);
            }
        });
        bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mainContext, "Edit pressed...", Toast.LENGTH_SHORT).show();
            }
        });
        ivSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mainContext, "Size", Toast.LENGTH_SHORT).show();
                flFrame.setVisibility(View.INVISIBLE);
                flFrameTop.setVisibility(View.INVISIBLE);
                bEdit.setVisibility(View.INVISIBLE);
                bSend.setVisibility(View.INVISIBLE);
            }
        });
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
