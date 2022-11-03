package com.devbeginner.testtasksitec.model.response;

import com.devbeginner.testtasksitec.model.User;

import java.util.ArrayList;

public class Users {
    ArrayList<User> listUsers;
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
