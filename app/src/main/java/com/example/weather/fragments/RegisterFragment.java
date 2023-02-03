package com.example.weather.fragments;

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
import android.widget.Toast;

import com.example.weather.R;
import com.example.weather.activities.viewModel.LandingViewModel;
import com.example.weather.databinding.FragmentRegisterBinding;
import com.example.weather.interfaces.DatePickerInterface;

public class RegisterFragment extends Fragment {

//    private View view;
//    private EditText mobileNumberEditText, calenderEditText, pinCodeEditText, districtEditText, stateEditText, nameEditText
//          , addressEditText, address2Edittext;
//    private  Spinner genderSpinner;
//    private TextView registerBtn;
    private LandingViewModel landingViewModel;
    private DatePickerInterface datePickerInterface;
    private static final String TAG = "RegisterFragment";
    private NavController navController;
    private FragmentRegisterBinding fragmentRegisterBinding;

    //called after onCreateView
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        viewId();

        getMobileNumber();

        onClickCalender();

        getDetails();

        onClickRegister();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /* removed for ViewBinding
        // Inflate the layout for this fragment
//        view = inflater.inflate(R.layout.fragment_register, container, false);
        //        return view;*/
        landingViewModel = new ViewModelProvider(this).get(LandingViewModel.class);
        fragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater,container,false);
        return fragmentRegisterBinding.getRoot();
    }

    private void onClickRegister() {
        fragmentRegisterBinding.registerBtnTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(landingViewModel.onRegisterClick(requireActivity(),fragmentRegisterBinding.mobileET.getText().toString(),
                        fragmentRegisterBinding.nameET.getText().toString(),fragmentRegisterBinding.genderSP.getSelectedItem().toString(),
                        fragmentRegisterBinding.dobET.getText().toString(),fragmentRegisterBinding.address1ET.getText().toString(),
                        fragmentRegisterBinding.address2ET.getText().toString(),fragmentRegisterBinding.pinCodeET.getText().toString(),
                        fragmentRegisterBinding.districtET.getText().toString(), fragmentRegisterBinding.stateET.getText().toString())) {
                    navController.navigate(R.id.action_registerFragment_to_weatherFragment);
                }else{
                    Toast.makeText(requireContext(), ""+landingViewModel.gettingErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getDetails() {
        fragmentRegisterBinding.pinCodeET.addTextChangedListener(new TextWatcher() {
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
                    landingViewModel.hitAPI(Integer.valueOf(fragmentRegisterBinding.pinCodeET.getText().toString()),requireActivity(),datePickerInterface);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged: ");
            }
        });
    }

    private void onClickCalender() {
        fragmentRegisterBinding.dobET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                landingViewModel.openCalender(requireActivity(),datePickerInterface);
            }
        });
        fragmentRegisterBinding.dobET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                landingViewModel.openCalender(requireActivity(),datePickerInterface);
            }
        });
    }

    private void viewId() {
//        nameEditText = view.findViewById(R.id.name_ET);
//        addressEditText = view.findViewById(R.id.address1_ET);
//        address2Edittext = view.findViewById(R.id.address2_ET);
//        genderSpinner = view.findViewById(R.id.gender_SP);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireActivity(),
                R.array.array_gender, androidx.transition.R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fragmentRegisterBinding.genderSP.setAdapter(adapter);
//        mobileNumberEditText = view.findViewById(R.id.mobile_ET);
//        calenderEditText = view.findViewById(R.id.dob_ET);
//        pinCodeEditText = view.findViewById(R.id.pin_code_ET);
//        districtEditText = view.findViewById(R.id.district_ET);
//        stateEditText = view.findViewById(R.id.state_ET);
//        registerBtn = view.findViewById(R.id.register_btn_TV);
        datePickerInterface = new DatePickerInterface() {
            @Override
            public void settingDate(int year, int monthOfYear, int dayOfMonth) {
                fragmentRegisterBinding.dobET.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);
            }

            @Override
            public void setDistrictAndState(String district, String state, String division) {
                fragmentRegisterBinding.districtET.setText(district);
                fragmentRegisterBinding.stateET.setText(state);
            }
        };
    }

    private void getMobileNumber() {
        Bundle bundle = getArguments();
        String number = bundle.getString(getString(R.string.number));
        fragmentRegisterBinding.mobileET.setText(number);
    }
}