package com.example.lcogetpack.di.init;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.example.lcogetpack.data.utils.AppConstants;
import com.example.lcogetpack.di.api.ApiClient;
import com.example.lcogetpack.di.api.ApiInjectComponent;
import com.example.lcogetpack.di.api.DaggerApiInjectComponent;

public class ApplicationAppContext extends Application {
    private ApiInjectComponent networkComponent;
    private static Context APP_CONTEXT = null;
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        APP_CONTEXT = this;
        context = this;
        networkComponent = DaggerApiInjectComponent.builder()
                .apiClient(new ApiClient(AppConstants.BASE_URL))
                .build();
    }

    public static Context getAppContext() {
        return APP_CONTEXT;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public ApiInjectComponent getNetworkComponent() {
        return networkComponent;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }
}