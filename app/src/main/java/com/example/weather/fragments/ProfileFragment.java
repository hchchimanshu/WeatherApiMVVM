package com.example.weather.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weather.R;
import com.example.weather.activities.viewModel.LandingViewModel;


public class ProfileFragment extends Fragment {

    private View view;
    private TextView data;
    private LandingViewModel landingViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        landingViewModel = new ViewModelProvider(this).get(LandingViewModel.class);
        data = view.findViewById(R.id.details_TV);

        data.setText(landingViewModel.getDetailsFromPref(requireActivity()));

        return view;
    }
}