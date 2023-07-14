package com.example.weather.activities.repositories;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import com.example.weather.activities.constants.Constants;
import com.example.weather.interfaces.DatePickerInterface;

import java.util.Calendar;

public class LandingRepository {

    String mobileNumber;
    String errorMessage;

    public boolean onContinueClick(String mobileNumber){
        this.mobileNumber = mobileNumber;
        return checkValidations();
    }

    private boolean checkValidations() {
        if (mobileNumber.length()==Constants.ZERO)
        {
            setErrorMessage(Constants.PHONE_EMPTY_ERROR);
            return false;
        }
        else if (mobileNumber.length()< Constants.TEN)
        {
            setErrorMessage(Constants.PHONE_VALID_ERROR);
            return false;
        }
        else return true;

//        return false;
    }
    public String getErrorMessage(){
        return errorMessage;
    }

    public void setErrorMessage(String message){
        this.errorMessage = message;
    }


}
