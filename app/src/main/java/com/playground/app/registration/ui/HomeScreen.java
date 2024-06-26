package com.playground.app.registration.ui;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.hilt.navigation.HiltViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.playground.app.registration.R;
import com.playground.app.registration.databinding.HomeScreenBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeScreen extends Fragment {

    private HomeScreenBinding binding;
    private NavController navController;
    private HomeViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = HomeScreenBinding.inflate(inflater, container, false);
        navController = NavHostFragment.findNavController(this);
        NavBackStackEntry navBackStackEntry = navController.getBackStackEntry(R.id.nav_graph);
        viewModel = new ViewModelProvider(navBackStackEntry, HiltViewModelFactory.create(requireContext(), navBackStackEntry)).get(HomeViewModel.class);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.registerButton.setOnClickListener(button -> navController.navigate(HomeScreenDirections.actionHomeScreenToRegistrationNavGraph()));

        binding.loginButton.setOnClickListener(button -> {
            Editable username = binding.usernameInput.getText();
            Editable password = binding.passwordInput.getText();
            if (username != null && password != null) {
                viewModel.login(username.toString(), password.toString());
            }
        });

        viewModel.getHomeUiState().observe(getViewLifecycleOwner(), homeUiState -> {
            if (!homeUiState.getIsInitial()) {
                if (homeUiState.getUserDisplayInfo() != null) {
                    navController.navigate(HomeScreenDirections.actionHomeScreenToLoggedInHomeScreen());
                } else if (homeUiState.getError() != null) {
                    Toast.makeText(requireContext(), homeUiState.getError(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
