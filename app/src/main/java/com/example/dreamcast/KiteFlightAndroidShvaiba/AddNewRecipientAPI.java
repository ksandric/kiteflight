package com.example.dreamcast.KiteFlightAndroidShvaiba;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dreamcast.KiteFlightAndroidShvaiba.additional.JSONAdapterState;
import com.example.dreamcast.KiteFlightAndroidShvaiba.additional.JSONAdapterUser_Recipients;
import com.example.dreamcast.KiteFlightAndroidShvaiba.additional.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class AddNewRecipientAPI extends AppCompatActivity {

    String   apiURL       = "http://staging.api.kiteflightapp.com/v1/recipients/create";
    String   api_key      = "g4sksgk0kspscc4oogo8wow0w0ocossg000og0so";

    String   user_id = "NA";

    String[] states, facilities;
    String state, facilities_id = "";

    int total, i, iswitch = 0;

    Spinner sState, sFacilities;

    EditText etFirstName, etLastName, etID; //etText7;
    TextView tvInfo;

    Context mainContext;

    Button bSave, bCancel;

    JSONAdapterState jSONAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_recipient_api);

        //set the screen orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //configuration ActionBar
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add New Recipient");

        mainContext = this;

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");

        sState = (Spinner) findViewById(R.id.spinner);
        //sState.setPrompt("State");
        sFacilities = (Spinner) findViewById(R.id.spinner2);
        //sFacilities.setPrompt("Facilities");

        etFirstName = (EditText) findViewById(R.id.editTextFirstNameAPI);
        etLastName = (EditText) findViewById(R.id.editTextLastNameAPI);
        etID = (EditText) findViewById(R.id.editTextIDNumberAPI);
        tvInfo = (TextView) findViewById(R.id.textView9);

        //etText7 = (EditText) findViewById(R.id.editText7);

        bSave = (Button) findViewById(R.id.buttonSaveAPI);
        bCancel = (Button) findViewById(R.id.buttonCancelAPI);

        //etLastName.setText("testLN");
        //etFirstName.setText("testFN");
        //etID.setText("testID_01");

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etFirstName.length() == 0 || etLastName.length() == 0 || etID.length() == 0)
                {

                    tvInfo.setText(Html.fromHtml("<font COLOR='RED'>Please fill all fields</font>"));
                    Toast.makeText(mainContext, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                iswitch = 1;
                new UpdateTask(mainContext).execute(apiURL);

                Intent intent = new Intent(AddNewRecipientAPI.this, SelectRecipientAPI.class);
                //Intent intent = new Intent(this, MainPicture.class);
                intent.putExtra("user_id", user_id); // parameter transfer
                startActivity(intent);
            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewRecipientAPI.super.onBackPressed();
            }
        });


        etID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvInfo.setText("Please Enter Inmate Details ...");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvInfo.setText("Please Enter Inmate Details ...");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvInfo.setText("Please Enter Inmate Details ...");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




        sState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                Toast.makeText(getBaseContext(), states[position], Toast.LENGTH_SHORT).show();
                state = states[position].substring(0,2);
                if(iswitch == 2){
                    new UpdateTask(mainContext).execute(apiURL);
                }
                iswitch = 2;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        sFacilities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                Toast.makeText(getBaseContext(), facilities[position], Toast.LENGTH_SHORT).show();
                facilities_id = facilities[position].substring(0, facilities[position].indexOf(","));

                //state = states[position].substring(0,2);
                //etFirstName.setText(state);
//                if(iswitch == 2){
//                    new UpdateTask(mainContext).execute(apiURL);
//                }
//                iswitch = 2;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
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

            if (iswitch == 1) {
                params.add(new BasicNameValuePair("api_key", api_key));
                params.add(new BasicNameValuePair("user_id", user_id));
                params.add(new BasicNameValuePair("facilities_id", facilities_id));
                params.add(new BasicNameValuePair("resident_name_first", etFirstName.getText().toString()));
                params.add(new BasicNameValuePair("resident_name_last", etLastName.getText().toString()));
                params.add(new BasicNameValuePair("resident_id", etID.getText().toString()));
            }

            if(iswitch == 2){
                params.add(new BasicNameValuePair("api_key", api_key));
                params.add(new BasicNameValuePair("state", state));
            }

            else{
                params.add(new BasicNameValuePair("api_key", api_key));
            }

            JSONObject json = null;

            //send a request using the POST method
            if(iswitch == 1){
                json = jParser.makeHttpRequest(url, "POST", params);
            }
            if (iswitch == 2){
                json = jParser.makeHttpRequest("http://staging.api.kiteflightapp.com/v1/facilities/get_facilities_by_state", "GET", params);
            }
            else{
                json = jParser.makeHttpRequest("http://staging.api.kiteflightapp.com/v1/facilities/get_states", "GET", params);
            }

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



                    //etID.setText(jsonData.getJSONObject("data").toString());

                    if (iswitch == 1) Toast.makeText(mainContext, jsonData.getString("message"), Toast.LENGTH_SHORT).show();




                    if (iswitch == 2){
                        String jsonFacilities = "";
                        total = Integer.parseInt(jsonData.getString("total"));
                        if(total!=0) {


                            for (i = 0; i < total; i++) {
                                jsonFacilities += jsonData.getJSONArray("data").getString(i);
                            }
                            jsonFacilities = jsonFacilities.substring(6);
                            jsonFacilities = jsonFacilities.replaceAll("\"", "");
                            jsonFacilities = jsonFacilities.replaceAll(",mailing_state:", "");
                            jsonFacilities = jsonFacilities.replaceAll("name:", "");
                            jsonFacilities = jsonFacilities.replaceAll(state, " ");
                            facilities = jsonFacilities.split("id:");
                    //ЕСЛИ 0 ФАСИЛИТИ то очистить фасилити список
                            //etText7.setText(jsonFacilities);

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddNewRecipientAPI.this, android.R.layout.simple_spinner_item, facilities);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            sFacilities.setAdapter(adapter);
                        }

                        if (total == 0){
                            facilities = new String[] {"No facilities found for this state.,"};
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddNewRecipientAPI.this, android.R.layout.simple_spinner_item, facilities);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            sFacilities.setAdapter(adapter);
                        }
                    }

                    if(iswitch == 0){
                        String jsonStates = jsonData.getJSONObject("data").toString();
                        jsonStates = jsonStates.substring(1, jsonStates.length()-1);
                        jsonStates = jsonStates.replaceAll("\"","");
                        states = jsonStates.split(",");

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddNewRecipientAPI.this, android.R.layout.simple_spinner_item, states);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        sState.setAdapter(adapter);


                    }



                     //   etLastName.setText(total);
                    //}



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
                if (etFirstName.length() == 0 || etLastName.length() == 0 || etID.length() == 0)
                {
                    tvInfo.setText(Html.fromHtml("<font COLOR='RED'>Please fill all fields</font>"));
                    Toast.makeText(mainContext, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    iswitch = 1;
                    new UpdateTask(mainContext).execute(apiURL);

                    Intent intent = new Intent(AddNewRecipientAPI.this, SelectRecipientAPI.class);
                    //Intent intent = new Intent(this, MainPicture.class);
                    intent.putExtra("user_id", user_id); // parameter transfer
                    startActivity(intent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
