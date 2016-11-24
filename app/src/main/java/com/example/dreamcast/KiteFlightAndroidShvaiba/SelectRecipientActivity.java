package com.example.dreamcast.KiteFlightAndroidShvaiba;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.provider.MediaStore;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.dreamcast.KiteFlightAndroidShvaiba.additional.SelectUser;
import com.example.dreamcast.KiteFlightAndroidShvaiba.additional.SelectUserAdapter;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SelectRecipientActivity extends AppCompatActivity {

    Button addNew;
    int x =1;

    // ArrayList
    ArrayList<SelectUser> selectUsers;
    List<SelectUser> temp;
    // Contact List
    ListView listView;
    // Cursor to load contacts list
    Cursor phones, email;

    // Pop up
    ContentResolver   resolver;
    SearchView        search;
    SelectUserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_recipient);

        //set the screen orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //configuration ActionBar
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Select Recipient");

        addNew   = (Button)     findViewById(R.id.buttonAddNew);
        listView = (ListView)   findViewById(R.id.contacts_list);
        search   = (SearchView) findViewById(R.id.searchView);

        selectUsers = new ArrayList<SelectUser>();
        resolver    = this.getContentResolver();
        email       = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,null, null, null, ContactsContract.CommonDataKinds.Email.DATA + " ASC");
        phones      = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        LoadContact loadContact = new LoadContact();
        loadContact.execute();


        //*** setOnQueryTextListener ***
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

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectRecipientActivity.this,AddNewRecipientActivity.class);
                startActivity(intent);
            }
        });
    }
    ////
    class LoadContact extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Get Contact list from Phone
            if (phones != null) {
                Log.e("count", "" + phones.getCount());
                if (phones.getCount() == 0) {
                    Toast.makeText(SelectRecipientActivity.this, "No contacts in your contact list.", Toast.LENGTH_LONG).show();
                }

                while (phones.moveToNext() && email.moveToNext()) {
                    Bitmap bit_thumb = null;

                    String id = email.getString(email.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                    String name = email.getString(email.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = email.getString(email.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    String EmailAddr = email.getString(email.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    String image_thumb = email.getString(email.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));

                    try {
                        if (image_thumb != null) {
                            bit_thumb = MediaStore.Images.Media.getBitmap(resolver, Uri.parse(image_thumb));
                        } else {
                            Log.e("No Image Thumb", "--------------");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    SelectUser selectUser = new SelectUser();
                    selectUser.setThumb(bit_thumb);
                    selectUser.setName(name);
                    selectUser.setPhone(phoneNumber);
                    selectUser.setEmail(EmailAddr);
                    if(name==EmailAddr) selectUser.setEmail("no email");
                    selectUser.setCheckedBox(false);
                    selectUser.setRadioButton(false);
                    selectUsers.add(selectUser);
                }
            } else {
                Log.e("Cursor close 1", "----------------");
            }
            //phones.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter = new SelectUserAdapter(selectUsers, SelectRecipientActivity.this);
            listView.setAdapter(adapter);

            // Select item on listclick
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Log.e("search", "here---------------- listener");

                    SelectUser data = selectUsers.get(i);
                }
            });

            listView.setFastScrollEnabled(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        phones.close();
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
                startActivity(new Intent(this, SelectReturnActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
