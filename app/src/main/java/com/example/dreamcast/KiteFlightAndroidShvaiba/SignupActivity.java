package com.example.dreamcast.KiteFlightAndroidShvaiba;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dreamcast.KiteFlightAndroidShvaiba.additional.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class SignupActivity extends AppCompatActivity {

    Button regiser;
    EditText etEmailAddress;
    EditText etPassword;
    EditText etConfirmPassword;
    TextView tvText;

    Context mainContext;

    String apiURL       = "http://staging.api.kiteflightapp.com/v1/users/register";
    String api_key      = "g4sksgk0kspscc4oogo8wow0w0ocossg000og0so";

    String device_token = "test1Android";

    String name_first   = "test1Android";
    String name_last    = "test1Android";
    String testPassword = "test1Android";
    String testEmail    = "test1Android@test.com";
    String user_id = "NA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        mainContext = this;

        regiser           = (Button)   findViewById(R.id.buttonRegister);
        etEmailAddress    = (EditText) findViewById(R.id.editTextNewEmail);
        etPassword        = (EditText) findViewById(R.id.editTextNewPassword1);
        etConfirmPassword = (EditText) findViewById(R.id.editTextNewPassword2);
        tvText            = (TextView) findViewById(R.id.textView6);


        regiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailaddress = etEmailAddress.getText()+"";
                String password1    = etPassword.getText()+"";
                String password2    = etConfirmPassword.getText()+"";

                if (emailaddress.length() == 0 || password1.length() == 0 || password2.length() == 0)
                {
                    Toast.makeText(mainContext, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password1.equals(password2))
                {
                    new UpdateTask(mainContext).execute(apiURL);
                }
                else
                {
                    Toast.makeText(mainContext, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

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
            params.add(new BasicNameValuePair("name_first", name_first));
            params.add(new BasicNameValuePair("name_last", name_last));
            params.add(new BasicNameValuePair("email_address", etEmailAddress.getText()+""));
            params.add(new BasicNameValuePair("password", etPassword.getText()+""));
            params.add(new BasicNameValuePair("device_token", device_token));

            //send a request using the POST method
            JSONObject json = jParser.makeHttpRequest(url, "POST", params);

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
                    res = jsonData.getString("message");
                    user_id = jsonData.getJSONObject("data").getString("id");

                    if(res.equals("User registered successfully.")){
                        Toast.makeText(mainContext, res + " id " + user_id.toString(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignupActivity.this, MainMenu.class);
                        intent.putExtra("user_id", user_id); // parameter transfer
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(mainContext, res, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(mainContext, "null", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
