package com.shiftdev.postbud;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public abstract class Account implements Comparable<Account>, Serializable {
    final static String TAG = "Account";
    // Variables
    private String uid;     // user ID created by Firebase
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    // Constructors
    public Account() {}

    ;

    public Account(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Comparable implementation
    @Override
    public int compareTo(Account account) {
        return this.email.compareTo(account.email);
    }

    // Getters & Setters
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String accountId) {
        this.uid = accountId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Public methods
    public Parcel newParcel(String currentLocation, String origin, String destination, String orderedBy, String description, Parcel.Priority priority, Activity context) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        Parcel parcel = null;
        if (mAuth.getCurrentUser() != null) {
            parcel = new Parcel(currentLocation, origin, destination, orderedBy, description, priority, CurrentUserSingleton.getInstance().getCurrentUser().getUid(), context);
            FirebaseDatabase db = FirebaseDatabase.getInstance();

        }
        return parcel;
    }
}

