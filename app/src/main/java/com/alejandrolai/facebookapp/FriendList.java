package com.alejandrolai.facebookapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class FriendList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        ArrayList<Friend> friends;
        friends = getIntent().getParcelableArrayListExtra("data");

        final ListView listView = (ListView) findViewById(R.id.friendsListview);
        listView.setAdapter(new CustomAdapter(this, friends));

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


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final ImageView imageView = (ImageView) view.findViewById(R.id.contact_image);
                final BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
                final Bitmap bitmap = bitmapDrawable.getBitmap();
                Intent intent = new Intent(getApplicationContext(),ContactList.class);
                intent.putExtra("bitmap",bitmap);
                startActivityForResult(intent,1);
                return false;
            }
        });
    }

}
