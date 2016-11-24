package com.example.dreamcast.KiteFlightAndroidShvaiba;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class KiteCreditsActivity extends AppCompatActivity {

    private Button bPurchase;
    String user_id = "NA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kite_credits);

        //set the screen orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //configuration ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Credits");

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");

        bPurchase = (Button) findViewById(R.id.buttonPurchase);

        bPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KiteCreditsActivity.this, PurchaseActivity.class);
                intent.putExtra("user_id", user_id); // parameter transfer
                startActivity(intent);
            }
        });
    }
}
