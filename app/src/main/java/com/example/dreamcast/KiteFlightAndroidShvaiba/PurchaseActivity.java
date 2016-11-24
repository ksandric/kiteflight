package com.example.dreamcast.KiteFlightAndroidShvaiba;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

public class PurchaseActivity extends AppCompatActivity {

    private WebView webViewBC;
    Context mainContext;

    String  user_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");

        mainContext = this;

        webViewBC = (WebView) findViewById(R.id.webview_buy_credits);

        // Enable Javascript
        WebSettings webSettings = webViewBC.getSettings();
        webViewBC.setWebViewClient(new WebViewClient());
        webSettings.setJavaScriptEnabled(true);

        webViewBC.setVerticalScrollBarEnabled(false);
        //webViewBC.setInitialScale(225);
        webViewBC.scrollTo(0,100);

        webViewBC.loadUrl("http://staging.api.kiteflightapp.com/webviews/buy_credits/index/" + user_id);

        webViewBC.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                if(webViewBC.getTitle().equals("Fail")){
                    Toast.makeText(mainContext, "Payment Failed", Toast.LENGTH_LONG).show();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }

                if(webViewBC.getTitle().equals("Success")){
                    Toast.makeText(mainContext, "Payment Successful", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(PurchaseActivity.this, MainMenu.class);
                    intent.putExtra("user_id", user_id); // parameter transfer
                    startActivity(intent);
                }
                //if(webViewBC.getUrl().equals("http://staging.api.kiteflightapp.com/webviews/buy_credits/index/" + user_id)){
//                    webViewBC.evaluateJavascript("(function() { return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();",
//                            new ValueCallback<String>() {
//                                @Override
//                                public void onReceiveValue(String html) {
//                                    text.setText(html);
//                                    // code here
//                                }
//                            });
                    //webViewBC.findAll("Card");
                //}
//                else {
//                        webViewBC.findAll("for");
//
//                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(webViewBC.canGoBack()) {
            webViewBC.goBack();
        } else {
            //webViewBC.findAll("Card");
            super.onBackPressed();
        }
    }

}
