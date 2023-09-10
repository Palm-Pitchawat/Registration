package com.playground.app.registration;

import androidx.lifecycle.ViewModel;

import com.playground.app.registration.data.repository.UserRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {

    private final UserRepository userRepository;

    @Inject
    MainViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    void logout() {
        userRepository.logout();
    }
}
