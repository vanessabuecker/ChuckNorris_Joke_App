package com.vbuecker.chucknorrisio.datasource;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {
    static Retrofit retrofit (){
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ChuckNorrisApi.BASE_URL)
                .build();
    }
}
