package com.shiftdev.postbud;

public class CurrentUserSingleton {
    public static CurrentUserSingleton INSTANCE = null;

    public CurrentUserSingleton() {};

    public static CurrentUserSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CurrentUserSingleton();
        }
        return(INSTANCE);
    }
}
