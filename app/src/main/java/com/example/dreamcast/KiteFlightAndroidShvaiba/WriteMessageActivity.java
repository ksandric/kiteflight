package com.example.dreamcast.KiteFlightAndroidShvaiba;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WriteMessageActivity extends AppCompatActivity {


    EditText edittext;
    TextView textValue;
    int Value = 200; // message length
    Context mainContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_message);

        //configuration ActionBar
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mainContext = this;

        edittext  = (EditText) findViewById(R.id.editText);
        textValue = (TextView) findViewById(R.id.textValue);

        textValue.setText(Html.fromHtml("<font COLOR=\'BLACK\'> Wow..! You can send </font> <font COLOR=\'#FF2196F3\'>200 </font><font COLOR='BLACK'>Left Characters Message</font>"));

        edittext.addTextChangedListener(myEditTextListener);
        }

    private TextWatcher myEditTextListener = new TextWatcher() {
        @Override
        public void onTextChanged (CharSequence s, int start, int before, int count){
        if(Value - edittext.getText().length() >= 0){
            textValue.setText(Html.fromHtml("<font COLOR='BLACK'> Wow..! You can send </font> <font COLOR='#FF2196F3'>" + String.valueOf(Value - edittext.getText().length() + " </font><font COLOR='BLACK'>Left Characters Message</font>")));
        }
            else{
            textValue.setText(Html.fromHtml("<font COLOR='BLACK'> Wow..! You can send </font> <font COLOR='RED'>" + String.valueOf(Value - edittext.getText().length() + " </font><font COLOR='BLACK'>Left Characters Message</font>")));
        }

        }
        @Override
        public void afterTextChanged(Editable s) {
        }
        @Override
        public void beforeTextChanged (CharSequence s, int start, int count, int after){
        }
    };

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
                if (edittext.getText().length() == 0){
                    Toast.makeText(mainContext, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(this, PreviewActivity.class);
                    intent.putExtra("text", edittext.getText().toString());
                    startActivity(intent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
