package com.example.weather.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.weather.R;
import com.example.weather.activities.viewModel.LandingViewModel;

public class MainActivity extends AppCompatActivity {

    NavHostFragment navHostFragment;
    NavController navController;
    LandingViewModel landingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //navigation
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        //for title of the screen
        NavigationUI.setupActionBarWithNavController(this,navController);

        landingViewModel = new ViewModelProvider(this).get(LandingViewModel.class);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}