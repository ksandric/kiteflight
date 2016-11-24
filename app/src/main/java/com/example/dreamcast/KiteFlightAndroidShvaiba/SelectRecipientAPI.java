package com.example.dreamcast.KiteFlightAndroidShvaiba;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dreamcast.KiteFlightAndroidShvaiba.additional.JSONParser;
import com.example.dreamcast.KiteFlightAndroidShvaiba.additional.JSONAdapterUser_Recipients;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class SelectRecipientAPI extends AppCompatActivity {

    String   apiURL       = "http://staging.api.kiteflightapp.com/v1/recipients/users_recipients";
    String   api_key      = "g4sksgk0kspscc4oogo8wow0w0ocossg000og0so";

    String   user_id = "NA";

    int total;

    EditText text;
    Context  mainContext;
    Button bAddNew;

    ImageView ivSelect;

    ListView lvRecipients;
    JSONAdapterUser_Recipients jSONAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_recipient_api);

        //set the screen orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //configuration ActionBar
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Select Recipient");

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");

        mainContext = this;




        bAddNew = (Button) findViewById(R.id.buttonAddNewRcipientAPI);
        lvRecipients = (ListView) findViewById(R.id.contacts_listAPI);
        //ivSelect = (ImageView) findViewById(R.id.imageView25);

        bAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectRecipientAPI.this, AddNewRecipientAPI.class);
                intent.putExtra("user_id", user_id); // parameter transfer
                startActivity(intent);
            }
        });

        lvRecipients.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                Toast.makeText(getApplicationContext(), ((TextView) itemClicked.findViewById(R.id.textRecipientID)).getText(), Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(SelectRecipientAPI.this);
                builder.setTitle("Важное сообщение!")
                        .setMessage("Покормите кота!")
                        .setIcon(R.drawable.vector_delete)
                        .setCancelable(true)
                        .setNegativeButton("ОК, иду на кухню",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();

                return false;
            }
        });
        lvRecipients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                Toast.makeText(getApplicationContext(), ((TextView) itemClicked.findViewById(R.id.name2)).getText(), Toast.LENGTH_SHORT).show();
                if ( ((ImageView) itemClicked.findViewById(R.id.imageView25)).getVisibility() == View.VISIBLE) {
                    ((ImageView) itemClicked.findViewById(R.id.imageView25)).setVisibility(View.INVISIBLE);
                }
                else{
                    ((ImageView) itemClicked.findViewById(R.id.imageView25)).setVisibility(View.VISIBLE);
                }

                //Intent intent = new Intent(SelectRecipientAPI.this, MainPicture.class);
                //startActivity(intent);

            }
        });

        new UpdateTask(mainContext).execute(apiURL);

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

                    total = Integer.parseInt(jsonData.getString("total"));

                    //Toast.makeText(mainContext, jsonData.getString("message"), Toast.LENGTH_SHORT).show();

                    if (total != 0){
                        jSONAdapter = new JSONAdapterUser_Recipients(SelectRecipientAPI.this, jsonData.getJSONArray("data"));

                        //Set the above adapter as the adapter of choice for our list
                        lvRecipients.setAdapter(jSONAdapter);

                        res = jsonData.getJSONArray("data").getString(total);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(mainContext, "null", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_write_message, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            case R.id.action_next:
                Intent intent = new Intent(this, MainPicture.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
