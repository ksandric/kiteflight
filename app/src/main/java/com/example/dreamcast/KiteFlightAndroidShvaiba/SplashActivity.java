package com.example.dreamcast.KiteFlightAndroidShvaiba;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static com.example.dreamcast.KiteFlightAndroidShvaiba.additional.OtherMethods.checkConnection;


public class SplashActivity extends AppCompatActivity {

    Button login0;
    Button register;
    Context  mainContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mainContext = this;

        //configuration ActionBar
        getSupportActionBar().hide();

        login0   = (Button) findViewById(R.id.buttonLogin0);
        register = (Button) findViewById(R.id.buttonRegister);

        if ( !checkConnection(mainContext)) {
            Toast.makeText(mainContext, "No internet connection!", Toast.LENGTH_LONG).show();
        }

        login0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( !checkConnection(mainContext)) {
                    Toast.makeText(mainContext, "No internet connection!", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( !checkConnection(mainContext)) {
                    Toast.makeText(mainContext, "No internet connection!", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(SplashActivity.this, SignupActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}

