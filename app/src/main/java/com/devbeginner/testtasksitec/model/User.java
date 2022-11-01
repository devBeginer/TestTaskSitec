package com.devbeginner.testtasksitec.model;


import com.google.gson.annotations.SerializedName;

public class User {
    private String user;
    private String uid;
    private String language;

    public String getUser() {
        return user;
    }

    public String getUid() {
        return uid;
    }

    public String getLanguage() {
        return language;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public User(String user, String uid, String language){
        this.user = user;
        this.uid = uid;
        this.language = language;
    }


}
