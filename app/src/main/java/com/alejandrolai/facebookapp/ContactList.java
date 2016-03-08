package com.alejandrolai.facebookapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;


public class ContactList extends AppCompatActivity {


    private ListView contactListView;
    private ArrayList<Contact> contacts = new ArrayList<>();
    String name;
    String phoneNumber;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        Intent intent = getIntent();

         bitmap = intent.getParcelableExtra("bitmap");

        if (bitmap == null) {
            Toast.makeText(getApplicationContext(),"Bitmap is null",Toast.LENGTH_SHORT).show();
        }

        contactListView = (ListView) findViewById(R.id.contactsList);
        contactListView.setAdapter(new ContactsAdapter(this, R.layout.listview_friend, contacts));

        Cursor cursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,null, null);

        while (cursor.moveToNext()) {
            name = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Contact contact = new Contact(name, phoneNumber);
            contact.setName(name);
            contact.setPhoneNumber(phoneNumber);
            contacts.add(contact);
        }
        cursor.close();

        if (null != contacts && contacts.size() != 0) {
            Collections.sort(contacts, new Comparator<Contact>() {
                @Override
                public int compare(Contact lhs, Contact rhs) {
                    return lhs.getName().compareTo(rhs.getName());
                }
            });
        } else {
            Toast.makeText(this, "No Contact Found!!!", Toast.LENGTH_SHORT).show();
        }

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

        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = (Contact) contactListView.getItemAtPosition(position);
                String name = contact.getName();
                String phone = contact.getPhoneNumber();
                String contactId = Integer.toString(getContactId(phone, getApplicationContext()));
                if (bitmap != null) {
                    if (updatePhoto(contactId, name, phone, bitmap)) {
                        showDialog(true);
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    } else {
                        showDialog(false);
                        Toast.makeText(getApplicationContext(), "Failed to update", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Bitmap null", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static int getContactId(String number, Context context) {
        number = Uri.encode(number);
        int contactID = new Random().nextInt();
        Cursor cursor = context.getContentResolver().query(
                Uri.withAppendedPath(
                        ContactsContract.PhoneLookup.CONTENT_FILTER_URI,number),new String[] {
                        ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup._ID}, null, null, null);
        while (cursor.moveToNext()) {
            contactID = cursor.getInt(cursor.getColumnIndexOrThrow(ContactsContract.PhoneLookup._ID));
        }
        cursor.close();

        return  contactID;
    }

    boolean updatePhoto(String contactID, String contactName, String contactNumber, Bitmap bitmap){
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();
        ops.add(ContentProviderOperation
            .newUpdate(ContactsContract.Data.CONTENT_URI)
            .withSelection(ContactsContract.Data.CONTACT_ID + "=? AND " + ContactsContract.Data.MIMETYPE
                    + "=? ", new String[]{contactID, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE})
            .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, contactName)
            .build());
        ops.add(ContentProviderOperation
                .newUpdate(ContactsContract.Data.CONTENT_URI)
                .withSelection(ContactsContract.Data.CONTACT_ID + "=? AND " + ContactsContract.Data.MIMETYPE
                        + "=? AND " + ContactsContract.CommonDataKinds.Organization.TYPE + "=?"
                        , new String[]{contactID, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                        , String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)})
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contactNumber)
                .build());

        try {
            ByteArrayOutputStream image = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, image);

            ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection(ContactsContract.Data.CONTACT_ID + "=? AND " +
                            ContactsContract.Data.MIMETYPE + "=?", new String[]{contactID, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE})
                    .withValue(ContactsContract.Data.IS_SUPER_PRIMARY, 1)
                    .withValue(ContactsContract.Contacts.Photo.PHOTO, image.toByteArray())
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void showDialog(boolean success) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog,null);
        builder.setView(view);
        if  (success) {
            builder.setTitle("Success");
        } else {
            builder.setTitle("Failed to update");
        }
        builder.setNegativeButton( "Facebook Friends List",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_CANCELED, returnIntent);
                        finish();
                    }
                });
        builder.setPositiveButton("Exit",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
