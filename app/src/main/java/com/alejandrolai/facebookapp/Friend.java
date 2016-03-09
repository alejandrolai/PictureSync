package com.alejandrolai.facebookapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Friend implements Parcelable{
    private String mName;
    private String mId;

    public Friend(Parcel in){
        mName = in.readString();
        mId = in.readString();
    }

    public Friend(String name, String id) {
        this.mName = name;
        this.mId = id;
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
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mId);
    }
}
