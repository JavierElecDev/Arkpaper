package com.example.arckpaper.models;

public class User {
    private int id;
    private String email;
    private String password;

    public User( String email, String password) {

        this.email = email;
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean validateUser(){
        return email != null && !email.isEmpty() && password != null && !password.isEmpty();
    }

    public int insertUser(User user){
        return 1;
    }

}
