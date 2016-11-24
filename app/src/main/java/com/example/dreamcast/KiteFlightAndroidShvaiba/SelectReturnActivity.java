package com.example.dreamcast.KiteFlightAndroidShvaiba;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.dreamcast.KiteFlightAndroidShvaiba.additional.ReturnAddress;
import com.example.dreamcast.KiteFlightAndroidShvaiba.additional.ReturnAddressAdapter;

import java.util.ArrayList;
import java.util.Random;

public class SelectReturnActivity extends AppCompatActivity {
    Button     addNewReturnAddress;
    ArrayList<ReturnAddress> returnAddresss;
    ListView   listView;
    SearchView search;
    ReturnAddressAdapter adapter;
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_return);

        //configuration ActionBar
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Select Return Adress");

        addNewReturnAddress = (Button)     findViewById(R.id.buttonAddNewReturnAddress);
        listView            = (ListView)   findViewById(R.id.listViewReturnAddress);
        search              = (SearchView) findViewById(R.id.searchViewReturnAddress);

        returnAddresss = new ArrayList<ReturnAddress>();
        LoadReturnAddress load = new LoadReturnAddress();
        load.execute();

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return false;
            }
        });

        addNewReturnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectReturnActivity.this, AddNewReturnActivity.class);
                startActivity(intent);
            }
        });


    }

class LoadReturnAddress extends AsyncTask<Void, Void, Void> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(Void... voids) {

        for (int i=0; i<10; i++) {
            ReturnAddress returnAddress = new ReturnAddress();

            returnAddress.setName("RandomName"+i);
            returnAddress.setPhone("+375 " + random.nextInt(44) +" "+random.nextInt(9999999));
            returnAddress.setAdr1("RB Minsk");
            returnAddress.setAdr2("220020 Lenina "+i);

            returnAddresss.add(returnAddress);
        }
        return null;
}

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        adapter = new ReturnAddressAdapter(returnAddresss, SelectReturnActivity.this);
        listView.setAdapter(adapter);

        // Select item on listclick
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("search", "here---------------- listener");
                ReturnAddress data = returnAddresss.get(i);
            }
        });
        listView.setFastScrollEnabled(true);
    }
}

    @Override
    protected void onStop() {
        super.onStop();
    }

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
                startActivity(new Intent(this, PreviewActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
