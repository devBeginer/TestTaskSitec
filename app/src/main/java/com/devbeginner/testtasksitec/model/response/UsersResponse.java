package com.devbeginner.testtasksitec.model.response;

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
