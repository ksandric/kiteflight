package com.example.dreamcast.KiteFlightAndroidShvaiba;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dreamcast.KiteFlightAndroidShvaiba.additional.JSONParser;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

import static android.view.View.*;
import static com.example.dreamcast.KiteFlightAndroidShvaiba.additional.OtherMethods.checkConnection;

public class LoginActivity extends AppCompatActivity {

    //Facebook
    private LoginButton     loginButton;
    private CallbackManager callbackManager;

    TextView forgot; // Button "forgot"

    Button   bLogin, bLoginFacebook;
    CheckBox cbTest;

    Context  mainContext;

    EditText etEmailAddress, etPassword;

    String   apiURL       = "http://staging.api.kiteflightapp.com/v1/users/authenticate";
    String   api_key      = "g4sksgk0kspscc4oogo8wow0w0ocossg000og0so";

    String   device_token = "test1Android";
    String   testPassword = "test1Android";
    String   testEmail    = "test1Android@test.com";
    String   user_id = "NA";

    String sEmailFacebook = "", sFNFacebook = "", sLNFacebook = "";

    int iSwitch = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Facebook SDK (Login Facebook)
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_main);

        mainContext = this;

        AppEventsLogger.activateApp(this); //Facrbook event

        bLogin         = (Button)      findViewById(R.id.buttonLogin1);
        bLoginFacebook = (Button)      findViewById(R.id.buttonLogin2);
        forgot         = (TextView)    findViewById(R.id.textViewForgotPassword);
        loginButton    = (LoginButton) findViewById(R.id.login_button);
        cbTest         = (CheckBox)    findViewById(R.id.checkBox_forTest);
        etEmailAddress = (EditText)    findViewById(R.id.editTextEmail);
        etPassword     = (EditText)    findViewById(R.id.editTextPassword);

        if ( !checkConnection(mainContext)) {
            Toast.makeText(mainContext, "No internet connection!", Toast.LENGTH_LONG).show();
        }

        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Profile profile = Profile.getCurrentProfile();
                AccessToken accessToken = loginResult.getAccessToken();
                // Facebook Email address
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                try {
                                    sEmailFacebook = object.getString("email").toString();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();


                Toast.makeText(mainContext, "User ID: "
                        + loginResult.getAccessToken().getUserId()
                        + "\n" + "Auth Token: "
                        + loginResult.getAccessToken().getToken(), Toast.LENGTH_SHORT).show();

                sFNFacebook = profile.getFirstName();
                sLNFacebook = profile.getLastName();
                iSwitch = 1;
                new LoginActivity.UpdateTask(mainContext).execute(apiURL);
            }

            @Override
            public void onCancel() {
                Toast.makeText(mainContext, "Login attempt canceled.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(mainContext, "Login attempt failed.", Toast.LENGTH_SHORT).show();
            }
        });

        forgot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotActivity.class);
                startActivity(intent);
            }
        });


        bLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cbTest.isChecked()) {
                    etEmailAddress.setText("test1Android@test.com");
                    etPassword.setText("test1Android");
                }

                if (etEmailAddress.getText().length() == 0 || etPassword.getText().length() == 0)
                {
                    Toast.makeText(mainContext, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                else {
                    new LoginActivity.UpdateTask(mainContext).execute(apiURL);
                }

            }
        });

        bLoginFacebook.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.performClick();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
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

            JSONObject json = null;

            if (iSwitch == 1){
                params.add(new BasicNameValuePair("api_key", api_key));
                params.add(new BasicNameValuePair("email_address", sEmailFacebook));
                params.add(new BasicNameValuePair("name_first", sFNFacebook));
                params.add(new BasicNameValuePair("name_last", sLNFacebook));
                params.add(new BasicNameValuePair("password", "Facebook"));
                params.add(new BasicNameValuePair("device_token", device_token));
                //send a request using the POST method
                json = jParser.makeHttpRequest("http://staging.api.kiteflightapp.com/v1/users/register", "POST", params);
            }
            if(cbTest.isChecked()) {
                params.add(new BasicNameValuePair("api_key", api_key));
                params.add(new BasicNameValuePair("email_address", testEmail));
                params.add(new BasicNameValuePair("password", testPassword));
                params.add(new BasicNameValuePair("device_token", device_token));
                //send a request using the POST method
                json = jParser.makeHttpRequest(url, "POST", params);
            }
            if (iSwitch == 0) {
                params.add(new BasicNameValuePair("api_key", api_key));
                params.add(new BasicNameValuePair("email_address", etEmailAddress.getText().toString()));
                params.add(new BasicNameValuePair("password", etPassword.getText().toString()));
                params.add(new BasicNameValuePair("device_token", device_token));
                //send a request using the POST method
                json = jParser.makeHttpRequest(url, "POST", params);
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
                    res = jsonData.getString("message");
                    if (res.equals("User registered successfully.") || res.equals("User already exists.")) {
                        iSwitch = 0;
                        user_id = jsonData.getJSONObject("data").getString("id");
                        etEmailAddress.setText(sEmailFacebook.toString());
                        etPassword.setText("Facebook");
                        new LoginActivity.UpdateTask(mainContext).execute(apiURL);
//                        Intent intent = new Intent(LoginActivity.this, MainMenu.class);
//                        intent.putExtra("user_id", user_id); // parameter transfer
//                        intent.putExtra("user_name", sFNFacebook + " " + sLNFacebook + " " + sEmailFacebook);
//                        startActivity(intent);
                    }

                    if(res.equals("Logged in successfully.")) {
                        user_id = jsonData.getJSONObject("data").getString("id");

                        Toast.makeText(mainContext, res + " id " + user_id.toString(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainMenu.class);
                        intent.putExtra("user_id", user_id); // parameter transfer
                        intent.putExtra("user_name", jsonData.getJSONObject("data").getString("name_first") + " " + jsonData.getJSONObject("data").getString("email_address"));
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(mainContext, res, Toast.LENGTH_SHORT).show();
                        forgot.setVisibility(VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(mainContext, "null", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void onClickForgotPassword(View v) {
        Intent intent = new Intent(this, ForgotActivity.class);
        startActivity(intent);
    }
    public void onClickSignUP(View v) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}
