package com.example.nookatkg.service.impl;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class WeatherService {
    private OkHttpClient client;
    private Response response;
    private String cityName;
    private String unit;
    //Getting Data from OpenWeather API
    public JSONObject getWeather(){
        client = new OkHttpClient();  //using OKHTTP dependency . You have to add this mannually form OKHTTP website
        Request request = new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/weather?q=kzyl-kiya&appid=a6ab325a64b7a1305b74aa0941233269")
                .build();

        try {
            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        }catch (IOException | JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    //Getting required data from Weather JSON API
    //JSON Objects and JSON Arrays


    public JSONArray returnDayTemp() throws JSONException {
        JSONArray weatherJsonArray = getWeather().getJSONArray("main.temp");
        return weatherJsonArray;
    }

    public JSONObject returnMainObject() throws JSONException {
        JSONObject mainObject = getWeather().getJSONObject("main");
        return mainObject;
    }

// to  pull the values of Sys from JSON


    // Getters and Setters for CityName and Unit

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
