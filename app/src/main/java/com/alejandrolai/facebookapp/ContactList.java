package com.alejandrolai.facebookapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class ContactList extends AppCompatActivity {


    private ListView contactListView;
    private ArrayList<Contact> contacts = new ArrayList<>();
    String name;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        Intent intent = getIntent();

        String id = intent.getStringExtra("id");

        contactListView = (ListView) findViewById(R.id.contactsList);
        contactListView.setAdapter(new ContactsAdapter(this,R.layout.listview_friend,contacts));

        Cursor phonesNo = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,null, null);

        while (phonesNo.moveToNext()) {
            name = phonesNo.getString(phonesNo
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            phoneNumber = phonesNo.getString(phonesNo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            Contact contact = new Contact(name, phoneNumber);

            contact.setName(name);
            contact.setPhoneNumber(phoneNumber);
            contacts.add(contact);
        }
        phonesNo.close();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        contactListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = (Contact)contactListView.getItemAtPosition(position);
                Log.d("ContactList",contact.getName() + ", " + contact.getPhoneNumber());
                return false;
            }
        });
    }

}
