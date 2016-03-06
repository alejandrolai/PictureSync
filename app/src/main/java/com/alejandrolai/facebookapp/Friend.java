package com.alejandrolai.facebookapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Alejandro on 2/28/16.
 */
public class Friend implements Parcelable{
    private String name;
    private String id;

    public Friend(Parcel in){
        name = in.readString();
        id = in.readString();
    }

    public Friend(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public static final Creator<Friend> CREATOR = new Creator<Friend>() {
        @Override
        public Friend createFromParcel(Parcel in) {
            return new Friend(in);
        }

        @Override
        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(id);
    }
}
