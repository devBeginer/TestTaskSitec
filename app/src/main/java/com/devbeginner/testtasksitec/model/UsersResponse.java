package com.devbeginner.testtasksitec.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UsersResponse {
    Users users;

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public UsersResponse(Users users){
        this.users = users;
    }
}
