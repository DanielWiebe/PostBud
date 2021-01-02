package com.shiftdev.postbud;

import com.google.firebase.auth.FirebaseAuth;
public class CurrentUserSingleton {
    // Variables
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Account currentUser;

    // Instance
    public static CurrentUserSingleton INSTANCE = null;

    public CurrentUserSingleton() {};

    public static CurrentUserSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CurrentUserSingleton();
        }
        return(INSTANCE);
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public void setmAuth(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    public void setCurrentUser(Account account) {
        this.currentUser = account;
    }

    public Account getCurrentUser() {
        return currentUser;
    }
}
