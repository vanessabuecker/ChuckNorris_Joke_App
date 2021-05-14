package com.vbuecker.chucknorrisio.datasource;

import com.vbuecker.chucknorrisio.model.Joke;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ChuckNorrisApi {

    static final String BASE_URL = "https://api.chucknorris.io/";

    @GET("jokes/categories")
    Call<List<String>> findaAll();

    @GET("jokes/random")
    Call<Joke> findRandomBy(@Query("category")String category);


}
