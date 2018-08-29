package com.nanodegree.udacity.bakingapp.rest;

import com.google.gson.Gson;
import com.nanodegree.udacity.bakingapp.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BakingClient {

    private static BakingApi bakingApi = null;

    public static BakingApi getClient() {
        if (bakingApi == null) {
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .build();

            bakingApi = retrofit.create(BakingApi.class);
        }
        return bakingApi;
    }
}
