package com.company.weatherappproject9;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitWeather {

    private static Retrofit retrofit;
    public static  Retrofit getRetrofit(){
        if(retrofit==null){

        retrofit = new Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }


}
