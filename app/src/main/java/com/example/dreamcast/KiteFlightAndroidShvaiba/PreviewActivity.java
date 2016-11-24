package com.example.dreamcast.KiteFlightAndroidShvaiba;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class PreviewActivity extends AppCompatActivity {

    TextView tvPText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        //configuration ActionBar
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Preview Card");

        tvPText = (TextView) findViewById(R.id.textViewMessageTextPreview);

        Intent intent = getIntent();
        tvPText.setText(intent.getStringExtra("text"));
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
