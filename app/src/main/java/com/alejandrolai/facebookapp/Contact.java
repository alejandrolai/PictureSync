package com.alejandrolai.facebookapp;

public class Contact {

    private String mName;
    private String mPhoneNumber;

    public Contact(String name, String phoneNumber) {
        this.mName = name;
        this.mPhoneNumber = phoneNumber;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.mPhoneNumber = phoneNumber;
    }
}
