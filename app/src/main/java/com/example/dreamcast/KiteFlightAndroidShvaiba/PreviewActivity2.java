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

    TextView tvPText, tvBig;
    Context mainContext;
    ImageView mainImage, avatarImage, ivSize, ivBig1, ivBig2;
    FrameLayout flFrame, flFrameTop;
    Button bSend, bEdit, bBack;
    String sTextSource = "";

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
        tvBig = (TextView) findViewById(R.id.textViewPreviewTextBig);
        ivBig1 = (ImageView) findViewById(R.id.imageViewPreviewBig1);
        ivBig2 = (ImageView) findViewById(R.id.imageViewPreviewBig2);
        bBack = (Button) findViewById(R.id.buttonPreviewBigBack);

        Intent iintent = getIntent();
        sTextSource = iintent.getStringExtra("text");

        String sText = sTextSource;

        if (sText.length() > 17) sText = sText.substring(0, 17) + "\n" + sText.substring(17);
        if (sText.length() > 33) sText = sText.substring(0, 33) + "\n" + sText.substring(33);
        if (sText.length() > 52) sText = sText.substring(0, 52) + "\n" + sText.substring(52);
        if (sText.length() > 68) sText = sText.substring(0, 68) + "\n" + sText.substring(68);


        tvPText.setText(sText);

        Picasso.with(mainContext).load("file://" + mainContext.getExternalCacheDir().toString() + "/2.png").into(mainImage);
        Picasso.with(mainContext).load("file://" + mainContext.getExternalCacheDir().toString() + "/1.png").into(avatarImage);



        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mainContext, "Size", Toast.LENGTH_SHORT).show();
                flFrame.setVisibility(View.VISIBLE);
                flFrameTop.setVisibility(View.VISIBLE);
                bEdit.setVisibility(View.VISIBLE);
                bSend.setVisibility(View.VISIBLE);

                bBack.setVisibility(View.INVISIBLE);
                ivBig2.setVisibility(View.INVISIBLE);
                ivBig1.setVisibility(View.INVISIBLE);
                tvBig.setVisibility(View.INVISIBLE);
            }
        });
        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mainContext, "Sending...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PreviewActivity2.this, MainMenu.class);
                startActivity(intent);
            }
        });
        bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mainContext, "Edit pressed...", Toast.LENGTH_SHORT).show();
            }
        });
        ivSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mainContext, "Size", Toast.LENGTH_SHORT).show();
                flFrame.setVisibility(View.INVISIBLE);
                flFrameTop.setVisibility(View.INVISIBLE);
                bEdit.setVisibility(View.INVISIBLE);
                bSend.setVisibility(View.INVISIBLE);

                Picasso.with(mainContext).load("file://" + mainContext.getExternalCacheDir().toString() + "/2.png").into(ivBig1);
                Picasso.with(mainContext).load("file://" + mainContext.getExternalCacheDir().toString() + "/1.png").into(ivBig2);

                tvBig.setText(sTextSource);

                bBack.setVisibility(View.VISIBLE);
                ivBig2.setVisibility(View.VISIBLE);
                ivBig1.setVisibility(View.VISIBLE);
                tvBig.setVisibility(View.VISIBLE);
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
