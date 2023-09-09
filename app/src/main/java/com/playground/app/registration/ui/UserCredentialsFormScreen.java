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
import com.playground.app.registration.databinding.UserCredentialsFormScreenBinding;
import com.playground.app.registration.ui.model.UserCredentials;
import com.playground.app.registration.ui.util.MyTextWatcher;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class UserCredentialsFormScreen extends Fragment {

    private UserCredentialsFormScreenBinding binding;
    private NavController navController;
    private RegistrationViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = UserCredentialsFormScreenBinding.inflate(inflater, container, false);
        navController = NavHostFragment.findNavController(this);
        NavBackStackEntry navBackStackEntry = navController.getBackStackEntry(R.id.registration_nav_graph);
        viewModel = new ViewModelProvider(navBackStackEntry, HiltViewModelFactory.create(requireContext(), navBackStackEntry)).get(RegistrationViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.isUserCredentialsValid().observe(getViewLifecycleOwner(), (isValid) -> {
            if (isValid != null) {

                UserCredentials userCredentials = viewModel.getUserCredentials();

                if (!isValid) {

                    if (!userCredentials.getUsername().getIsValid()) {
                        binding.usernameInputBox.setErrorEnabled(true);
                        binding.usernameInputBox.setError(userCredentials.getUsername().getError().trim());
                    }

                    if (!userCredentials.getPassword().getIsValid()) {
                        binding.passwordInputBox.setErrorEnabled(true);
                        binding.passwordInputBox.setError(userCredentials.getPassword().getError().trim());
                    }

                } else {
                    navController.navigate(UserCredentialsFormScreenDirections.actionUserCredentialsFormScreenToUserCameraScreen());
                    viewModel.clearUserCredentialsValidation();
                }
            }
        });

        binding.usernameInput.addTextChangedListener((MyTextWatcher) editable -> viewModel.updateUsername(editable.toString()));
        binding.passwordInput.addTextChangedListener((MyTextWatcher) editable -> viewModel.updatePassword(editable.toString()));

        binding.submitBtn.setOnClickListener(button -> {
            binding.usernameInput.clearFocus();
            binding.passwordInput.clearFocus();
            viewModel.validateUserCredentials();
        });
        binding.usernameInput.setOnFocusChangeListener((button, hasFocus) -> {
            if (hasFocus) {
                binding.usernameInputBox.setError(null);
                binding.usernameInputBox.setErrorEnabled(false);
            }
        });
        binding.passwordInput.setOnFocusChangeListener((button, hasFocus) -> {
            if (hasFocus) {
                binding.passwordInputBox.setError(null);
                binding.passwordInputBox.setErrorEnabled(false);
            }
        });
    }
}
