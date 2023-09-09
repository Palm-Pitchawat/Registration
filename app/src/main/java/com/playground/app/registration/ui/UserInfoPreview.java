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

import com.playground.app.registration.R;
import com.playground.app.registration.databinding.UserInfoPreviewScreenBinding;
import com.playground.app.registration.ui.model.UserCredentials;
import com.playground.app.registration.ui.model.UserPersonalInfo;

public class UserInfoPreview extends Fragment {

    private UserInfoPreviewScreenBinding binding;
    private NavController navController;
    private RegistrationViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = UserInfoPreviewScreenBinding.inflate(inflater, container, false);
        navController = NavHostFragment.findNavController(this);
        NavBackStackEntry navBackStackEntry = navController.getBackStackEntry(R.id.registration_nav_graph);
        viewModel = new ViewModelProvider(navBackStackEntry, HiltViewModelFactory.create(requireContext(), navBackStackEntry)).get(RegistrationViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UserPersonalInfo userPersonalInfo = viewModel.getUserPersonalInfo();
        UserCredentials userCredentials = viewModel.getUserCredentials();

        binding.userPicture.setImageURI(userPersonalInfo.getPictureUri());
        binding.citizenIdText.setText(userPersonalInfo.getCitizenId().getText());
        binding.firstNameText.setText(userPersonalInfo.getFirstName().getText());
        binding.lastNameText.setText(userPersonalInfo.getLastName().getText());
        binding.phoneNumberText.setText(userPersonalInfo.getPhoneNumber().getText());
        binding.emailText.setText(userPersonalInfo.getEmail().getText());
        binding.usernameText.setText(userCredentials.getUsername().getText());

        binding.submitBtn.setOnClickListener(button -> {
            viewModel.register();
        });
    }
}
