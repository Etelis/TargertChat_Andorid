package com.example.targertchat.data.remote;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * RetrofitService - Retrofit Service class.
 */
public class RetrofitService {
    // Default_URL.
    public static String DEFAULT_URL = "http://10.0.2.2:7129/api/";
    // BASE_URL usage for sending the current request.
    private static String BASE_URL = DEFAULT_URL;

    /**
     * createService - create new retrofit service object from provided service.
     * @param serviceClass - desired service.
     * @param BASE_URL - the BASE URL for the service.
     * @param <S> - desired service.
     * @return - retrofit service
     */
    public static <S> S createService(Class<S> serviceClass, String... BASE_URL){
        // check if a BASE_URL was recived, if not use default.
        if (BASE_URL.length != 0)
            RetrofitService.BASE_URL = "http://" + BASE_URL[0];

        // create rertofit object.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient()
                        .create()))
                .build();

        return retrofit.create(serviceClass);
    }

}
