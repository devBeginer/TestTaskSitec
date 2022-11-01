package com.devbeginner.testtasksitec.model;

import java.util.ArrayList;

public class Users {
    //@SerializedName("ListUsers")
    ArrayList<User> listUsers;
    //@SerializedName("CurrentUid")
    String currentUid;

    public ArrayList<User> getListUsers() {
        return listUsers;
    }

    public String getCurrentUid() {
        return currentUid;
    }

    public void setListUsers(ArrayList<User> listUsers) {
        this.listUsers = listUsers;
    }

    public void setCurrentUid(String currentUid) {
        this.currentUid = currentUid;
    }

    public Users(ArrayList<User> listUsers, String currentUid) {
        this.listUsers = listUsers;
        this.currentUid = currentUid;
    }

    @Override
    public String toString() {
        return "UsersResponse{" +
                "listUsers=" + listUsers +
                ", currentUid='" + currentUid + '\'' +
                '}';
    }
}
