package com.company.weatherappproject9;

import com.company.weatherappproject9.models.WeatherApp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("weather?appid=6dffb534eb73036445cef8f8558c246b&units=metric")
    Call<WeatherApp>getWeatherWithCityNames(@Query("q")String name);
}
