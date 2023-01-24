package com.example.weather.activities.repositories;

import android.content.Context;

import com.example.weather.R;
import com.example.weather.activities.constants.Constants;
import com.example.weather.interfaces.GettingWeatherResultInterface;
import com.example.weather.models.WeatherDetailPojo;
import com.example.weather.preferences.Preferences;
import com.example.weather.retrofit.Api;
import com.example.weather.retrofit.RetrofitData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {

    private Api api;

    public String getCityFromPref(Context context) {
        return Preferences.getInstance().getSomeStringValue(context.getString(R.string.city));
    }

    public String getPinCodeFromPref(Context context) {
        return String.valueOf(Preferences.getInstance().getSomeIntValue(context.getString(R.string.pincode)));
    }

    public String getStateFromPref(Context context) {
        return Preferences.getInstance().getSomeStringValue(context.getString(R.string.state));
    }

    //profile repo
    public String getDetailsFromPref(Context context) {
        return Preferences.getInstance().getSomeStringValue(context.getString(R.string.name))+"\n"+
        Preferences.getInstance().getSomeStringValue(context.getString(R.string.gender))+", "+
                Preferences.getInstance().getSomeIntValue(context.getString(R.string.age))+"\n"+
                Preferences.getInstance().getSomeStringValue(context.getString(R.string.address1))+", "+
                Preferences.getInstance().getSomeStringValue(context.getString(R.string.address2))+"\n"+
                Preferences.getInstance().getSomeStringValue(context.getString(R.string.state))+", "+
                Preferences.getInstance().getSomeIntValue(context.getString(R.string.pincode));
    }

    public void hitApi(String city,Context context, GettingWeatherResultInterface resultInterface) {

//        Log.d(TAG, "hitApi: ");

        api = RetrofitData.getRetrofit(Constants.URL).create(Api.class);

        Call<WeatherDetailPojo> pojoCall = api.getWeatherDetail(city,Constants.KEY);

        pojoCall.enqueue(new Callback<WeatherDetailPojo>() {
            @Override
            public void onResponse(Call<WeatherDetailPojo> call, Response<WeatherDetailPojo> response) {
//                Log.e(TAG, "onResponse: " );
//                mainActivity.dismissProgressBar();
                resultInterface.settingWeatherResult(String.valueOf(context.getString(R.string.temperature)+
                        response.body().main.getTemp()+ context.getString(R.string.max_temperature)+
                        response.body().main.getTemp_max()+response.body().main.getTemp_min() +
                        context.getString(R.string.humidity)+response.body().main.getHumidity()+
                        context.getString(R.string.clouds)+response.body().clouds.getAll()));
//                result.setText(String.valueOf(getString(R.string.temperature)+response.body().main.getTemp()+
//                        getString(R.string.max_temperature)+response.body().main.getTemp_max()+
//                        +response.body().main.getTemp_min() +
//                        getString(R.string.humidity)+response.body().main.getHumidity()+
//                        getString(R.string.clouds)+response.body().clouds.getAll()));
            }

            @Override
            public void onFailure(Call<WeatherDetailPojo> call, Throwable t) {
//                Log.d(TAG, "onFailure: ");
//                mainActivity.dismissProgressBar();
            }
        });
    }
}
