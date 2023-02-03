package com.example.weather.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.weather.activities.viewModel.LandingViewModel;
import com.example.weather.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {

//    private View view;
//    private TextView data;
    private LandingViewModel landingViewModel;
    FragmentProfileBinding fragmentProfileBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        landingViewModel = new ViewModelProvider(this).get(LandingViewModel.class);

        fragmentProfileBinding = FragmentProfileBinding.inflate(inflater,container,false);
        return fragmentProfileBinding.getRoot();
//        view = inflater.inflate(R.layout.fragment_profile, container, false);


//        data = view.findViewById(R.id.details_TV);

//        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentProfileBinding.detailsTV.setText(landingViewModel.getDetailsFromPref(requireActivity()));

    }
}