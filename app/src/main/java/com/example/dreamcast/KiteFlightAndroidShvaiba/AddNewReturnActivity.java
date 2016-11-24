package com.example.dreamcast.KiteFlightAndroidShvaiba;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class AddNewReturnActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_return);

        //set the screen orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //configuration ActionBar
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add New Return Adress");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //selection layout file res\menu\menu.xml
        getMenuInflater().inflate(R.menu.menu_write_message, menu);
        return true;
    }

    // Handle action bar item clicks here.
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //startActivity(new Intent(this, LoginActivity.class));
                super.onBackPressed();
                return true;
            case R.id.action_next:
                startActivity(new Intent(this, PreviewActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
