package com.alejandrolai.facebookapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context context;
    private ArrayList<Friend> friends = new ArrayList<Friend>();
    LayoutInflater inflater;

    public CustomAdapter(Context context, ArrayList<Friend> friendsList){
        this.context = context;
        this.friends = friendsList;
        inflater = LayoutInflater.from(this.context);
    }
    @Override
    public int getCount() {
        return friends.size();
    }

    @Override
    public Friend getItem(int position) {
        return friends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_friend,parent, false);
        }

        Friend friend = getItem(position);

        ImageView profilePicture = (ImageView) convertView.findViewById(R.id.contact_image);
        Picasso.with(context).load("https://graph.facebook.com/"+friend.getId()+"/picture?type=large").into(profilePicture);


        TextView name = (TextView) convertView.findViewById(R.id.contact_name);
        name.setText(friend.getName());

        return convertView;
    }
}
