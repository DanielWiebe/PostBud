package com.shiftdev.postbud;

public abstract class Account {
    // Variables
    private String userName;
    private String password;

    // Constructors
    public Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    // Getters & Setters
    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}

