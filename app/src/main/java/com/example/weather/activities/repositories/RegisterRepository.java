package com.example.weather.activities.repositories;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.weather.R;
import com.example.weather.activities.MainActivity;
import com.example.weather.activities.constants.Constants;
import com.example.weather.interfaces.DatePickerInterface;
import com.example.weather.models.Pojo;
import com.example.weather.models.PostOfficePojo;
import com.example.weather.preferences.Preferences;
import com.example.weather.retrofit.Api;
import com.example.weather.retrofit.RetrofitData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRepository {

    private RegisterRepository()
    {
    }

    public static RegisterRepository one_instance = null;

    public static RegisterRepository getInstance()
    {
        if (one_instance==null)
        {
            one_instance= new RegisterRepository();
        }
        return one_instance;
    }

    private Api api;
    private ArrayList<PostOfficePojo> pojoArrayList;
    private static final String TAG = "RegisterRepository";
    String mobileNumber, name, gender, dob, address1, address2, pinCode, district, state;
    String errorMessage;

    public void openCalender(FragmentActivity context, DatePickerInterface datePickerInterface) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        // date picker dialog
        new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
//                        return String.valueOf(dayOfMonth + "/"
//                                + (monthOfYear + 1) + "/" + year);
                        datePickerInterface.settingDate(year,monthOfYear,dayOfMonth);
//                        dob.setText(dayOfMonth + "/"
//                                + (monthOfYear + 1) + "/" + year);
//                        age = getAge(year,monthOfYear,dayOfMonth);

                    }
                }, mYear, mMonth, mDay).show();
        hideKeyboard(context);
    }

    private int getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
//        String ageS = ageInt.toString();

//        return ageS;
        return ageInt;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), Constants.ZERO);
    }

    public void hitAPI(int pinCodeNum, FragmentActivity context, DatePickerInterface datePickerInterface) {
        api = RetrofitData.getRetrofit(Constants.URL2).create(Api.class);

        Call<List<Pojo>> pojoCall = api.getPinCode(pinCodeNum);

        pojoCall.enqueue(new Callback<List<Pojo>>() {
            @Override
            public void onResponse(Call<List<Pojo>> call, Response<List<Pojo>> response) {
                Log.d(TAG, "onResponse: ");
//                mainActivity.dismissProgressBar();
                if (response.body().get(Constants.ZERO).getStatus().equals(context.getString(R.string.success))) {
//                    datePickerInterface.setDistrictAndState(pojoArrayList.get(Constants.ZERO).getDistrict(),
//                            pojoArrayList.get(Constants.ZERO).getState());
                    pojoArrayList = response.body().get(Constants.ZERO).getPostOffice();
                    datePickerInterface.setDistrictAndState(pojoArrayList.get(Constants.ZERO).getDistrict(),
                            pojoArrayList.get(Constants.ZERO).getState(),pojoArrayList.get(Constants.ZERO).getDivision());
//                    district.setText(pojoArrayList.get(Constants.ZERO).getDistrict());
//                    state.setText(pojoArrayList.get(Constants.ZERO).getState());
                    hideKeyboard(context);

                }
                else
                {
                    Toast.makeText(context, "Incorrect PinCode", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pojo>> call, Throwable t) {
//                mainActivity.dismissProgressBar();
                Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    public boolean onRegisterClick(Context context,String mobileNumber, String name, String gender, String dob, String address,
                                   String address2, String pinCode, String district, String state) {
        this.mobileNumber = mobileNumber;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.address1 = address;
        this.address2 = address2;
        this.pinCode = pinCode;
        this.district = district;
        this.state = state;
        return checkValidations(context);
    }

    private boolean checkValidations(Context context) {
        if (mobileNumber.length()==Constants.ZERO)
        {
            settingErrorMessage(context.getString(R.string.phone_empty_error));
        }
        else if (mobileNumber.length()<Constants.TEN)
        {
            settingErrorMessage(context.getString(R.string.phone_valid_error));
        }
        else if (name.length()== Constants.ZERO)
        {
            settingErrorMessage(context.getString(R.string.name_empty_error));
        }
        else if (name.length()< Constants.FOUR)
        {
            settingErrorMessage(context.getString(R.string.name_valid_error));
        }
        else if (gender.length()==Constants.ZERO)
        {
            settingErrorMessage(context.getString(R.string.gender_empty_error));
        }
        else if (gender.length()<Constants.FOUR)
        {
            settingErrorMessage(context.getString(R.string.gender_select_error));
        }
        else if (gender.equals("Gender *"))
        {
            settingErrorMessage(context.getString(R.string.gender_select_error));
        }
        else if (dob.length()==Constants.ZERO)
        {
            settingErrorMessage(context.getString(R.string.dob_empty_error));
        }
        else if (address1.length()==Constants.ZERO)
        {
            settingErrorMessage(context.getString(R.string.address_empty_error));
        }
        else if (address1.length()<Constants.FOUR)
        {
            settingErrorMessage(context.getString(R.string.address_valid_error));
        }
        else if (pinCode.length()==Constants.ZERO)
        {
            settingErrorMessage(context.getString(R.string.pincode_empty_error));
        }
        else if (pinCode.length()<Constants.SIX)
        {
            settingErrorMessage(context.getString(R.string.pincode_valid_error));
        }
        else if (district.length()==Constants.ZERO)
        {
            settingErrorMessage(context.getString(R.string.district_empty_error));
        }
        else if (state.length()==Constants.ZERO)
        {
            settingErrorMessage(context.getString(R.string.state_empty_error));
        }
        else {
            saveInPreferences(context);
            return true;
        }

        return false;
    }

    private void saveInPreferences(Context context) {
        Preferences.getInstance().setSomeStringValue(context.getString(R.string.hint_mobile),mobileNumber);
        Preferences.getInstance().setSomeStringValue(context.getString(R.string.name),name);
        Preferences.getInstance().setSomeStringValue(context.getString(R.string.gender),gender);
        Preferences.getInstance().setSomeStringValue(context.getString(R.string.dob),dob);
//        Preferences.getInstance().setSomeIntValue(context.getString(R.string.age),age);
        Preferences.getInstance().setSomeStringValue(context.getString(R.string.city),pojoArrayList.get(Constants.ZERO).getDivision());
        Preferences.getInstance().setSomeStringValue(context.getString(R.string.address1),address1);
        Preferences.getInstance().setSomeStringValue(context.getString(R.string.address2),address2);
        Preferences.getInstance().setSomeIntValue(context.getString(R.string.pincode),Integer.valueOf(pinCode));
        Preferences.getInstance().setSomeStringValue(context.getString(R.string.district),district);
        Preferences.getInstance().setSomeStringValue(context.getString(R.string.state),state);
    }

    public String gettingErrorMessage(){
        return errorMessage;
    }

    public void settingErrorMessage(String message){
        this.errorMessage = message;
    }
}
