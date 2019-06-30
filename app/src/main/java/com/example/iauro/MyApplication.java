package com.example.iauro;

import android.support.multidex.MultiDexApplication;

import com.example.iauro.networks.ApiRequests;
import com.example.iauro.utils.CommonUtil;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends MultiDexApplication {
    private static MyApplication instance;
    private static Retrofit retrofit;
    private static ApiRequests mApi;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        getRetrofitInstance();
    }

    private void getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(CommonUtil.getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        mApi = retrofit.create(ApiRequests.class);
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public static ApiRequests getApi(){
        return mApi;
    }
}
