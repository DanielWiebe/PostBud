package com.shiftdev.postbud;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executor;

public abstract class Account implements Comparable<Account> {
    // Variables
    private String uid;     // user ID created by Firebase
    private String email;
    private String password;

    // Constructors
    public Account() {}

    public Account(String email, String password, Activity context) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(context, (OnCompleteListener<AuthResult>) task -> {
                if (task.isSuccessful()){
                    setEmail(email);
                    setPassword(password);
                }
            });
    }

    // Comparable implementation
    @Override
    public int compareTo(Account account) {
        return this.uid.compareTo(account.uid);
    }

    // Getters & Setters
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountId() {
        return uid;
    }

    public void setAccountId(String accountId) {
        this.uid = accountId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

