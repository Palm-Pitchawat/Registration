package com.playground.app.registration.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.playground.app.registration.data.repository.UserRepository;
import com.playground.app.registration.data.util.Result;
import com.playground.app.registration.ui.model.HomeUiState;
import com.playground.app.registration.ui.model.UserDisplayInfo;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HomeViewModel extends ViewModel {

    private final UserRepository userRepository;

    @Inject
    HomeViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final MutableLiveData<HomeUiState> _homeUiState = new MutableLiveData<>(new HomeUiState(true, null, null));

    public LiveData<HomeUiState> getHomeUiState() {
        return _homeUiState;
    }

    public void login(String username, String password) {
        userRepository.login(username, password, result -> {
            if (result instanceof Result.Success) {
                _homeUiState.postValue(new HomeUiState(
                    false,
                    ((Result.Success<UserDisplayInfo>) result).data,
                    null)
                );
            }
            if (result instanceof Result.Error) {
                _homeUiState.postValue(new HomeUiState(
                    false,
                    null,
                    ((Result.Error<UserDisplayInfo>) result).exception.getMessage()
                ));
            }
        });
    }
}
