package com.mobdeve.s17.charcookery.api.models;

public class CreateAccountBody {
    public String email;
    public String password;

    public CreateAccountBody(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
