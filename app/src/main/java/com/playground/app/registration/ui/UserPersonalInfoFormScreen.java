package com.playground.app.registration.ui;

import android.os.Bundle;
import android.util.Log;
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
import com.playground.app.registration.databinding.UserPersonalInfoFormScreenBinding;
import com.playground.app.registration.ui.model.UserPersonalInfo;
import com.playground.app.registration.ui.util.MyTextWatcher;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class UserPersonalInfoFormScreen extends Fragment {

    private UserPersonalInfoFormScreenBinding binding;
    private NavController navController;
    private RegistrationViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = UserPersonalInfoFormScreenBinding.inflate(inflater, container, false);
        navController = NavHostFragment.findNavController(this);
        NavBackStackEntry navBackStackEntry = navController.getBackStackEntry(R.id.registration_nav_graph);
        viewModel = new ViewModelProvider(navBackStackEntry, HiltViewModelFactory.create(requireContext(), navBackStackEntry)).get(RegistrationViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.isUserPersonalInfoValid().observe(getViewLifecycleOwner(), isValid -> {
            if (isValid != null) {

                UserPersonalInfo userPersonalInfo = viewModel.getUserPersonalInfo();

                if (!isValid) {

                    if (!userPersonalInfo.getCitizenId().getIsValid()) {
                        binding.citizenIdInputBox.setErrorEnabled(true);
                        binding.citizenIdInputBox.setError(userPersonalInfo.getCitizenId().getError().trim());
                    }

                    if (!userPersonalInfo.getFirstName().getIsValid()) {
                        binding.firstNameInputBox.setErrorEnabled(true);
                        binding.firstNameInputBox.setError(userPersonalInfo.getFirstName().getError().trim());
                    }

                    if (!userPersonalInfo.getLastName().getIsValid()) {
                        binding.lastNameInputBox.setErrorEnabled(true);
                        binding.lastNameInputBox.setError(userPersonalInfo.getLastName().getError().trim());
                    }

                    if (!userPersonalInfo.getPhoneNumber().getIsValid()) {
                        binding.phoneNumberInputBox.setErrorEnabled(true);
                        binding.phoneNumberInputBox.setError(userPersonalInfo.getPhoneNumber().getError().trim());
                        Log.i("test", userPersonalInfo.getPhoneNumber().getError().trim());
                    }

                    if (!userPersonalInfo.getEmail().getIsValid()) {
                        binding.emailInputBox.setErrorEnabled(true);
                        binding.emailInputBox.setError(userPersonalInfo.getEmail().getError().trim());
                    }

                } else {
                    navController.navigate(UserPersonalInfoFormScreenDirections.actionUserPersonalInfoFormScreenToUserCredentialsFormScreen());
                    viewModel.clearUserPersonalValidation();
                }
            }
        });

        binding.citizenIdInput.addTextChangedListener((MyTextWatcher) editable -> viewModel.updateCitizenId(editable.toString()));
        binding.firstNameInput.addTextChangedListener((MyTextWatcher) editable -> viewModel.updateFirstName(editable.toString()));
        binding.lastNameInput.addTextChangedListener((MyTextWatcher) editable -> viewModel.updateLastName(editable.toString()));
        binding.phoneNumberInput.addTextChangedListener((MyTextWatcher) editable -> viewModel.updatePhoneNumber(editable.toString()));
        binding.emailInput.addTextChangedListener((MyTextWatcher) editable -> viewModel.updateEmail(editable.toString()));

        binding.submitBtn.setOnClickListener(button -> {
            binding.citizenIdInput.clearFocus();
            binding.firstNameInput.clearFocus();
            binding.lastNameInput.clearFocus();
            binding.phoneNumberInput.clearFocus();
            binding.emailInput.clearFocus();
            viewModel.validateUserPersonalInfo();
        });
        binding.citizenIdInput.setOnFocusChangeListener((button, hasFocus) -> {
            if (hasFocus) {
                binding.citizenIdInputBox.setError(null);
                binding.citizenIdInputBox.setErrorEnabled(false);
            }
        });
        binding.firstNameInput.setOnFocusChangeListener((button, hasFocus) -> {
            if (hasFocus) {
                binding.firstNameInputBox.setError(null);
                binding.firstNameInputBox.setErrorEnabled(false);
            }
        });
        binding.lastNameInput.setOnFocusChangeListener((button, hasFocus) -> {
            if (hasFocus) {
                binding.lastNameInputBox.setError(null);
                binding.lastNameInputBox.setErrorEnabled(false);
            }
        });
        binding.phoneNumberInput.setOnFocusChangeListener((button, hasFocus) -> {
            if (hasFocus) {
                binding.phoneNumberInputBox.setError(null);
                binding.phoneNumberInputBox.setErrorEnabled(false);
            }
        });
        binding.emailInput.setOnFocusChangeListener((button, hasFocus) -> {
            if (hasFocus) {
                binding.emailInputBox.setError(null);
                binding.emailInputBox.setErrorEnabled(false);
            }
        });
    }
}
