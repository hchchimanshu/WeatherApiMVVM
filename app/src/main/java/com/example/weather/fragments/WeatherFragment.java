package com.example.weather.fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather.R;
import com.example.weather.activities.viewModel.LandingViewModel;
import com.example.weather.interfaces.DatePickerInterface;
import com.example.weather.interfaces.GettingWeatherResultInterface;
import com.example.weather.preferences.Preferences;


public class WeatherFragment extends Fragment {

    EditText pinCode, state, city;
    TextView showResult, result, check;
    View view;
    LandingViewModel landingViewModel;
    GettingWeatherResultInterface resultInterface;
    DatePickerInterface datePickerInterface;
    NavController navController;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
            pinCode = view.findViewById(R.id.pincode_ET);
            state = view.findViewById(R.id.state_ap_ET);
            city = view.findViewById(R.id.city_ap_ET);
            showResult = view.findViewById(R.id.show_result_btn_TV);
            result = view.findViewById(R.id.result_TV);
            check = view.findViewById(R.id.check_btn_TV);
            setHasOptionsMenu(true);
            resultInterface = new GettingWeatherResultInterface() {
                @Override
                public void settingWeatherResult(String resultValue) {
                    result.setText(resultValue);
                }
            };

            datePickerInterface = new DatePickerInterface() {
                @Override
                public void settingDate(int year, int month, int day) {}

                @Override
                public void setDistrictAndState(String district, String stateValue, String division) {
                    state.setText(stateValue);
                    city.setText(division);
                }
            };
//            mainActivity = (MainActivity) getActivity();
//            pojoArrayList =  new ArrayList<>();
//            mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //hide back arrow
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        view = inflater.inflate(R.layout.fragment_weather, container, false);

        landingViewModel = new ViewModelProvider(this).get(LandingViewModel.class);

        initId();
//        pinCode = view.findViewById(R.id.pincode_ET);
//        state = view.findViewById(R.id.state_ap_ET);
//        city = view.findViewById(R.id.city_ap_ET);

        getDetails();

        onClickCheckPinCode();

        onClickShowResults();



//        OnBackPressedCallback callback = new OnBackPressedCallback(
//                true // default to enabled
//        ) {
//            @Override
//            public void handleOnBackPressed() {
////                Toast.makeText(requireActivity(), "no back", Toast.LENGTH_SHORT).show();
////                showAreYouSureDialog();
//            }
//        };
//
//        requireActivity().getOnBackPressedDispatcher().addCallback(
//                requireActivity(),callback);
        return view;
    }

    private void initId() {
//        navController = Navigation.findNavController(view);
        pinCode = view.findViewById(R.id.pincode_ET);
        state = view.findViewById(R.id.state_ap_ET);
        city = view.findViewById(R.id.city_ap_ET);
        showResult = view.findViewById(R.id.show_result_btn_TV);
        result = view.findViewById(R.id.result_TV);
        check = view.findViewById(R.id.check_btn_TV);
        resultInterface = new GettingWeatherResultInterface() {
            @Override
            public void settingWeatherResult(String resultValue) {
                result.setText(resultValue);
            }
        };

        datePickerInterface = new DatePickerInterface() {
            @Override
            public void settingDate(int year, int month, int day) {}

            @Override
            public void setDistrictAndState(String district, String stateValue, String division) {
                state.setText(stateValue);
                city.setText(division);
            }
        };
    }

    private void onClickCheckPinCode() {
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                landingViewModel.hitAPI(Integer.valueOf(pinCode.getText().toString()),requireActivity(),datePickerInterface);
            }
        });
    }

    private void onClickShowResults() {
        showResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                landingViewModel.hitAPI(city.getText().toString(),requireActivity(),resultInterface);
            }
        });
    }

    private void getDetails() {
        city.setText(landingViewModel.getCityFromPref(requireActivity()));
        pinCode.setText(landingViewModel.getPinCodeFromPref(requireActivity()));
        state.setText(landingViewModel.getStateFromPref(requireActivity()));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        MainActivity mainActivity = (MainActivity)getActivity();
        switch (item.getItemId())
        {
            case R.id.my_profile:

//                ProfileFragment fragment = new ProfileFragment();
//                mainActivity.replaceFragment(R.id.container_FL,fragment);
                navController.navigate(R.id.action_weatherFragment_to_profileFragment);

                break;

            case R.id.logout:
//                LandingFragment fragment1 = new LandingFragment();
//                mainActivity.replaceFragment(R.id.container_FL,fragment1);
                navController.navigate(R.id.action_weatherFragment_to_landingFragment);

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}