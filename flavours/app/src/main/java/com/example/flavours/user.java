package com.example.flavours;

public class user {


    private String email;
    private String name;
    private String username;
    private String mobilenumber;

    public user(){

    }

    public user(String email, String name, String username, String mobilenumber) {
        this.email = email;
        this.name = name;
        this.username = username;
        this.mobilenumber = mobilenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }
}
