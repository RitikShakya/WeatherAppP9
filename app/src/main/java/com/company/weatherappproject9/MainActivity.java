package com.company.weatherappproject9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.company.weatherappproject9.models.WeatherApp;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    TextView location,weather, temp,humidity,maxtemp,mintemp,pressure,wind;
    EditText input;
    Button search;
    ImageView imageView;

    LocationManager locationManager;
    LocationListener locationListener;
    Double lon,lat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.image);
        location = findViewById(R.id.cityname);
        weather = findViewById(R.id.weather);
        temp = findViewById(R.id.temp1);
        humidity = findViewById(R.id.humidity);
        maxtemp = findViewById(R.id.maxtemp);
        mintemp = findViewById(R.id.mintemp);
        pressure = findViewById(R.id.pressure);
        wind = findViewById(R.id.wind);
        input = findViewById(R.id.inputcity);
        search = findViewById(R.id.searchbtn);

        locationManager =(LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                lat = location.getLatitude();
                lon = location.getLongitude();

                Log.e("lon" , String.valueOf(lon));


            }
        };

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1 );
        }else{

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,50, 50,locationListener);
        }


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = input.getText().toString();
                getWeatherdata(name);


            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==1 && permissions.length>0 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,50, 50,locationListener);

        }
    }

   public void getWeatherdata(String name){

      // Retrofit retrofit = new Retrofit.Builder()
       WeatherApi weatherApi = RetrofitWeather.getRetrofit().create(WeatherApi.class);

       Call<WeatherApp> weatherAppCall =weatherApi.getWeatherWithCityNames(name);

       weatherAppCall.enqueue(new Callback<WeatherApp>() {
           @Override
           public void onResponse(Call<WeatherApp> call, Response<WeatherApp> response) {

               if(response.isSuccessful()) {
                   location.setText(response.body().getName() + " " + response.body().getSys().getCountry());

                   temp.setText(response.body().getMain().getTemp() + "C");
                   weather.setText(response.body().getWeather().get(0).getDescription());
                   humidity.setText(response.body().getMain().getHumidity() + "");
                   maxtemp.setText(response.body().getMain().getTempMax() + "C");
                   mintemp.setText(response.body().getMain().getTempMin() + "C");
                   pressure.setText(response.body().getMain().getPressure()+"");
                   wind.setText(response.body().getWind().getSpeed() + "");

//                   String iconcode = response.body().getWeather().get(0).getIcon();
//                   Picasso.get().load("").placeholder(R.drawable.ic_launcher_background).into(imageView);

               }
               else{
                   Toast.makeText(MainActivity.this, "sorry not available", Toast.LENGTH_SHORT).show();
               }

           }

           @Override
           public void onFailure(Call<WeatherApp> call, Throwable t) {


           }
       });





   }
}