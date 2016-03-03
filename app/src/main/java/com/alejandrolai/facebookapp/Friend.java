package com.alejandrolai.facebookapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Alejandro on 2/28/16.
 */
public class Friend implements Parcelable{
    private String name;
    private String id;
    private String photoUrl;
    private String email;

    public Friend(Parcel in){
        name = in.readString();
        id = in.readString();
        photoUrl = in.readString();
        email = in.readString();
    }

    public Friend(String name, String id, String photoUrl) {
        this.name = name;
        this.id = id;
        this.photoUrl = photoUrl;
    }

    public Friend(String name, String id) {
        this.name = name;
        this.id = id;
        this.photoUrl = photoUrl;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(id);
        dest.writeString(photoUrl);
        dest.writeString(email);
    }
}
