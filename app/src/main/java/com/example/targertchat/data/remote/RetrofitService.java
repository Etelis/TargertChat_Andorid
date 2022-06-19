package com.example.targertchat.data.remote;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    public static String DEFAULT_URL = "http://10.0.2.2:7129/api/";
    private static String BASE_URL = DEFAULT_URL;
    public static <S> S createService(Class<S> serviceClass, String... BASE_URL){
        String URL = RetrofitService.BASE_URL;

        if (BASE_URL.length != 0)
            RetrofitService.BASE_URL = "http://" + BASE_URL[0];

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient()
                        .create()))
                .build();

        return retrofit.create(serviceClass);
    }

}
