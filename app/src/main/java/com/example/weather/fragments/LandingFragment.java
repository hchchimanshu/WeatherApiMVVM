package com.example.weather.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather.R;
import com.example.weather.activities.viewModel.LandingViewModel;
import com.example.weather.databinding.FragmentLandingBinding;

public class LandingFragment extends Fragment {

//    TextView continueButton;
//    EditText mobileNumberEditText;
    private LandingViewModel landingViewModel;
    private NavController navController;
    private FragmentLandingBinding fragmentLandingBinding;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);


        fragmentLandingBinding.continueBtnTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if(landingViewModel.onContinueClick(fragmentLandingBinding.mobET.getText().toString())) {
                if(landingViewModel.onContinueClick(fragmentLandingBinding)) {
                    Bundle bundle = new Bundle();
                    bundle.putString(getString(R.string.number),fragmentLandingBinding.mobET.getText().toString());
                    navController.navigate(R.id.action_landingFragment_to_registerFragment,bundle);
                }else{
                    Toast.makeText(requireContext(), ""+landingViewModel.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        /* to hide the toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        to show it again on next fragment
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();

        //hide back arrow from toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        */

        landingViewModel = new ViewModelProvider(this).get(LandingViewModel.class);

        fragmentLandingBinding = FragmentLandingBinding.inflate(inflater,container,false);
        return fragmentLandingBinding.getRoot();
        /* removed for using ViewBinding
//        View view = inflater.inflate(R.layout.fragment_landing, container, false);
//
//        continueButton = view.findViewById(R.id.continue_btn_TV);
//        mobileNumberEditText = view.findViewById(R.id.mob_ET);
//        return view;

         */
    }
}