package com.example.dreamcast.KiteFlightAndroidShvaiba;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dreamcast.KiteFlightAndroidShvaiba.additional.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class MainMenu extends AppCompatActivity {

    ImageView imageButtonSend, imageButtonHistory;
    TextView  creditBalance, tvUserInfo;
    FrameLayout flCreditBalanceText;

    Context   mainContext;

    String    apiURL  = "http://staging.api.kiteflightapp.com/v1/credits/users_available_credits";
    String    api_key = "g4sksgk0kspscc4oogo8wow0w0ocossg000og0so";
    String    user_id = "", user_name = "";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mainContext = this;

        imageButtonHistory = (ImageView) findViewById(R.id.imageViewButtonOrderHistory);
        imageButtonSend    = (ImageView) findViewById(R.id.imageViewButtonSendKite);
        creditBalance      = (TextView)  findViewById(R.id.textViewValueCreditBalance);
        tvUserInfo         = (TextView)  findViewById(R.id.textViewUserInfo);
        flCreditBalanceText= (FrameLayout) findViewById(R.id.frameMenu2);

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        user_name = intent.getStringExtra("user_name");
        tvUserInfo.setText(user_name + "\nid " + user_id);

        creditBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, PurchaseActivity.class);
                intent.putExtra("user_id", user_id); // parameter transfer
                startActivity(intent);
            }
        });
        flCreditBalanceText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, PurchaseActivity.class);
                intent.putExtra("user_id", user_id); // parameter transfer
                startActivity(intent);
            }
        });

        new MainMenu.UpdateTask(mainContext).execute(apiURL);
    }

    //work with api
    public class UpdateTask extends AsyncTask<String, Void, JSONObject> {
        Context context;

        public UpdateTask(Context context) {
            super();
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... urls) {
            return loadJSON(urls[0]);
        }

        public JSONObject loadJSON(String url) {

            JSONParser jParser = new JSONParser();
            // adding parameters in the query
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("api_key", api_key));
            params.add(new BasicNameValuePair("user_id", user_id));

            //send a request using the GET method
            JSONObject json = jParser.makeHttpRequest(url, "GET", params);

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject jsonData) {
            // possible errors, check for null
            if (jsonData != null) {
                super.onPostExecute(jsonData);
                String res = "";
                try {
                    //read parameter that sent the server
                    res = jsonData.getString("total");

                    creditBalance.setText(res);

                    //Toast.makeText(mainContext, jsonData.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                //Toast.makeText(mainContext, "null", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void onClickSendKite(View v) {
        Intent intent = new Intent(this, SelectRecipientAPI.class);
        //Intent intent = new Intent(this, MainPicture.class);
        intent.putExtra("user_id", user_id); // parameter transfer
        startActivity(intent);
    }
    public void onClickOrderHistory(View v) {
        Intent intent = new Intent(this, OrderHistoryActivity.class);
        startActivity(intent);
    }

    public void onClickCredits(View v) {
        //Intent intent = new Intent(this, KiteCreditsActivity.class);
        Intent intent = new Intent(this, PurchaseActivity.class);
        intent.putExtra("user_id", user_id); // parameter transfer
        startActivity(intent);
    }

    public void onClickAdressBook(View v) {
        Intent intent = new Intent(this, SelectReturnActivity.class);
        startActivity(intent);
    }
}
