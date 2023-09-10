package com.playground.app.registration.data.repository;

import com.google.firebase.auth.FirebaseUser;
import com.playground.app.registration.data.repository.util.RepositoryCallBack;
import com.playground.app.registration.ui.model.UserCredentials;
import com.playground.app.registration.ui.model.UserDisplayInfo;
import com.playground.app.registration.ui.model.UserPersonalInfo;

public interface RegistrationRepository {
    void registerWithFirebaseAuth(UserPersonalInfo userPersonalInfo, UserCredentials userCredentials, RepositoryCallBack<FirebaseUser> callBack);
}
