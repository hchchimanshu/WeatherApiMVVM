package com.example.weather.fragments;

import android.app.ActionBar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather.R;
import com.example.weather.activities.viewModel.LandingViewModel;
import com.example.weather.interfaces.DatePickerInterface;

public class RegisterFragment extends Fragment {

    private View view;
    private EditText mobileNumberEditText, calenderEditText, pinCodeEditText, districtEditText, stateEditText, nameEditText
            , addressEditText, address2Edittext;
    private  Spinner genderSpinner;
    private TextView registerBtn;
    private LandingViewModel landingViewModel;
    private DatePickerInterface datePickerInterface;
    private static final String TAG = "RegisterFragment";
    private NavController navController;

    //called after onCreateView
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);
        landingViewModel = new ViewModelProvider(this).get(LandingViewModel.class);

        viewId();

        getMobileNumber();

        onClickCalender();

        getDetails();

        onClickRegister();

        return view;
    }

    private void onClickRegister() {
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(landingViewModel.onRegisterClick(requireActivity(),mobileNumberEditText.getText().toString(),
                        nameEditText.getText().toString(),genderSpinner.getSelectedItem().toString(),
                        calenderEditText.getText().toString(),addressEditText.getText().toString(),
                        address2Edittext.getText().toString(),pinCodeEditText.getText().toString(),
                        districtEditText.getText().toString(), stateEditText.getText().toString())) {
                    navController.navigate(R.id.action_registerFragment_to_weatherFragment);
                }else{
                    Toast.makeText(requireContext(), ""+landingViewModel.gettingErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getDetails() {
        pinCodeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged: ");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged: ");
                if(s.length()>5){
//                    mainActivity.showProgressDialog();
                    Log.d(TAG, "onTextChanged: >5 ");
                    landingViewModel.hitAPI(Integer.valueOf(pinCodeEditText.getText().toString()),requireActivity(),datePickerInterface);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged: ");
            }
        });
    }

    private void onClickCalender() {
        calenderEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                landingViewModel.openCalender(requireActivity(),datePickerInterface);
            }
        });
        calenderEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    landingViewModel.openCalender(requireActivity(),datePickerInterface);
            }
        });
    }

    private void viewId() {
        nameEditText = view.findViewById(R.id.name_ET);
        addressEditText = view.findViewById(R.id.address1_ET);
        address2Edittext = view.findViewById(R.id.address2_ET);
        genderSpinner = view.findViewById(R.id.gender_SP);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireActivity(),
                R.array.array_gender, androidx.transition.R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);
        mobileNumberEditText = view.findViewById(R.id.mobile_ET);
        calenderEditText = view.findViewById(R.id.dob_ET);
        pinCodeEditText = view.findViewById(R.id.pin_code_ET);
        districtEditText = view.findViewById(R.id.district_ET);
        stateEditText = view.findViewById(R.id.state_ET);
        registerBtn = view.findViewById(R.id.register_btn_TV);
        datePickerInterface = new DatePickerInterface() {
            @Override
            public void settingDate(int year, int monthOfYear, int dayOfMonth) {
                calenderEditText.setText(dayOfMonth + "/"
                        + (monthOfYear + 1) + "/" + year);
            }

            @Override
            public void setDistrictAndState(String district, String state, String division) {
                districtEditText.setText(district);
                stateEditText.setText(state);
            }
        };
    }

    private void getMobileNumber() {
        Bundle bundle = getArguments();
        String number = bundle.getString(getString(R.string.number));
        mobileNumberEditText.setText(number);
    }
}