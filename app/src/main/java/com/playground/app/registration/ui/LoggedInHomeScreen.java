package com.playground.app.registration.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.hilt.navigation.HiltViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.playground.app.registration.R;
import com.playground.app.registration.databinding.LoggedInHomeScreenBinding;

public class LoggedInHomeScreen extends Fragment {

    private LoggedInHomeScreenBinding binding;
    private NavController navController;
    private HomeViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = LoggedInHomeScreenBinding.inflate(inflater, container, false);
        navController = NavHostFragment.findNavController(this);
        NavBackStackEntry navBackStackEntry = navController.getBackStackEntry(R.id.nav_graph);
        viewModel = new ViewModelProvider(navBackStackEntry, HiltViewModelFactory.create(requireContext(), navBackStackEntry)).get(HomeViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getHomeUiState().observe(getViewLifecycleOwner(), homeUiState -> {
            if (!homeUiState.getIsInitial()) {
                Glide.with(requireContext()).load(homeUiState.getUserDisplayInfo().getPictureUri()).into(binding.userPicture);
                binding.userFullName.setText(String.format(
                    "%s %s",
                    homeUiState.getUserDisplayInfo().getFirstName(),
                    homeUiState.getUserDisplayInfo().getLastName())
                );
                binding.userEmail.setText(homeUiState.getUserDisplayInfo().getEmail());
                binding.userPhoneNumber.setText(homeUiState.getUserDisplayInfo().getPhoneNumber());
                binding.userCitizenId.setText(homeUiState.getUserDisplayInfo().getCitizenId());
            }
        });
    }
}
