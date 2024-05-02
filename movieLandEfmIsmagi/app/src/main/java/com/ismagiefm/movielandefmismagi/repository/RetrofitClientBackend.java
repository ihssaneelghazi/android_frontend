package com.ismagiefm.movielandefmismagi.repository;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClientBackend {
    private static Retrofit retrofit;//instance de retrofit
    private static final String BASE_URL = "http://192.168.0.101:8081/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {//si l'instance de retrofit est nulle alors on la crée
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())//on utilise Gson pour convertir les réponses en objets Java
                    .build();
        }
        return retrofit;
    }
}