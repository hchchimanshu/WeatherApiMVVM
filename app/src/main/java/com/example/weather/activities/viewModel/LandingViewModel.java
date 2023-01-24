package com.example.weather.activities.viewModel;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;

import com.example.weather.activities.repositories.LandingRepository;
import com.example.weather.activities.repositories.RegisterRepository;
import com.example.weather.activities.repositories.WeatherRepository;
import com.example.weather.interfaces.DatePickerInterface;
import com.example.weather.interfaces.GettingWeatherResultInterface;

public class LandingViewModel extends ViewModel {

    LandingRepository landingRepository = new LandingRepository();
    RegisterRepository registerRepository = new RegisterRepository();
    WeatherRepository weatherRepository = new WeatherRepository();

    public boolean onContinueClick(String mobileNumber){
        return landingRepository.onContinueClick(mobileNumber);
    }

    public String getErrorMessage(){
        return landingRepository.getErrorMessage();
    }

    //register fragment
    public void openCalender(FragmentActivity context, DatePickerInterface datePickerInterface){
        registerRepository.openCalender(context, datePickerInterface);
    }

    public void hitAPI(int pinCode, FragmentActivity context, DatePickerInterface datePickerInterface) {
        registerRepository.hitAPI(pinCode,context, datePickerInterface);
    }

    public boolean onRegisterClick(Context context,String mobileNumber, String name, String gender, String dob,
                        String address, String address2, String pinCode, String district,
                                   String state){
        return registerRepository.onRegisterClick(context,mobileNumber,name,gender,dob,address,address2,pinCode,district,state);
    }
    public String gettingErrorMessage(){
        return registerRepository.gettingErrorMessage();
    }


    public String getCityFromPref(Context context) {
        return weatherRepository.getCityFromPref(context);
    }

    public String getPinCodeFromPref(Context context) {
        return weatherRepository.getPinCodeFromPref(context);
    }

    public String getStateFromPref(Context context) {
        return weatherRepository.getStateFromPref(context);
    }

    public void hitAPI(String city, FragmentActivity context, GettingWeatherResultInterface resultInterface) {
        weatherRepository.hitApi(city,context, resultInterface);
    }

    public String getDetailsFromPref(Context context) {
        return weatherRepository.getDetailsFromPref(context);
    }
}
