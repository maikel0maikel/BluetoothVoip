package com.maikel.blutoothvoip;

import android.app.Application;
import android.content.Context;

/**
 * Created by maikel on 2018/3/25.
 */

public class BluetoothApplication extends Application{
    private static Context contextInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        contextInstance = this;
    }
    public static Context getContextInstance(){
        return contextInstance;
    }
}
