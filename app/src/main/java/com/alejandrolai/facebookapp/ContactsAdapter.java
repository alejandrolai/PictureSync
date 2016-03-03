package com.alejandrolai.facebookapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Alejandro on 3/2/16.
 */
public class ContactsAdapter extends ArrayAdapter<Contact> {

    private Context context;
    private int row;
    private ArrayList<Contact> contacts;

    public ContactsAdapter(Context context,int row, ArrayList<Contact> contacts) {
        super(context,row,contacts);
        this.context = context;
        this.row = row;
        this.contacts = contacts;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Contact getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_contact,parent,false);
        }

        Contact contact = getItem(position);

        TextView name = (TextView) convertView.findViewById(R.id.contactName);
        name.setText(contact.getName());

        TextView number = (TextView) convertView.findViewById(R.id.contactPhone);
        number.setText(contact.getPhoneNumber());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return super.getFilter();
    }

    public class ViewHolder {
        public TextView contactName, contactNumber;
    }
}
