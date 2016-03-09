package com.alejandrolai.facebookapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FriendList extends AppCompatActivity {

    private View myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        ArrayList<Friend> friends;
        friends = getIntent().getParcelableArrayListExtra("data");

        final ListView listView = (ListView) findViewById(R.id.friendsListview);
        listView.setAdapter(new FriendsAdapter(this, friends));

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


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                myView = view;
                int permission;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    permission = checkSelfPermission(android.Manifest.permission.WRITE_CONTACTS);
                    if (permission != PackageManager.PERMISSION_GRANTED) {
                        if (!shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_CONTACTS)) {
                            showMessageOKCancel("You need to allow access to Contacts",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[] {Manifest.permission.WRITE_CONTACTS},
                                                        1);
                                            }
                                        }
                                    });
                            return;
                        }
                        requestPermissions(new String[]{android.Manifest.permission.WRITE_CONTACTS},
                                1);
                        return;
                    }
                } else {
                    sendBitmap();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    sendBitmap();
                } else {
                    // Permission Denied
                    Toast.makeText(FriendList.this, "WRITE_CONTACTS Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void sendBitmap(){
        final ImageView imageView = (ImageView) myView.findViewById(R.id.contact_image);
        final BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
        final Bitmap bitmap = bitmapDrawable.getBitmap();
        Intent intent = new Intent(getApplicationContext(), ContactList.class);
        intent.putExtra("bitmap", bitmap);
        startActivityForResult(intent, 1);
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(FriendList.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}
