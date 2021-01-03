package com.shiftdev.postbud;

public class CurrentUserSingleton {
    // Instance
    public static CurrentUserSingleton INSTANCE = null;
    // Variables
    Account currentUser;

    public CurrentUserSingleton() {}

    public static CurrentUserSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CurrentUserSingleton();
        }
        return (INSTANCE);
    }

    public Account getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Account account) {
        this.currentUser = account;
    }
}
