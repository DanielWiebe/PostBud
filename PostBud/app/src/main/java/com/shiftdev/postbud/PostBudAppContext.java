package com.shiftdev.postbud;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

public class PostBudAppContext extends Application {
    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context mContext) {
        PostBudAppContext.mContext = mContext;
    }

    public static Activity getActivity() {
        return (Activity) mContext;
    }
}
