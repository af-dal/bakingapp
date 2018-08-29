package com.nanodegree.udacity.bakingapp.rest;


import com.nanodegree.udacity.bakingapp.data.model.Recipe;

import java.util.List;

import retrofit2.Callback;

public class BakingRepo {

    private final BakingApi bakingApi;

    public BakingRepo() {
        bakingApi = BakingClient.getClient();
    }

    public void loadRecipeList(final Callback<List<Recipe>> callback) {
        bakingApi.getRecipeList().enqueue(callback);
    }
}
