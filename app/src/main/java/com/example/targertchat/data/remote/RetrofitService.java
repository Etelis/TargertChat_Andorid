package com.example.targertchat.data.remote;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private static String BASE_URL = "http://10.0.2.2:7129/api/";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                    .setLenient()
                    .create()))
            .build();

    public static <S> S createService(Class<S> serviceClass, String... BASE_URL){

        if (BASE_URL.length != 0)
            RetrofitService.BASE_URL = BASE_URL[0];

        return retrofit.create(serviceClass);
    }

}
