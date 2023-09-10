package com.playground.app.registration.data.repository;

import com.playground.app.registration.data.repository.util.RepositoryCallBack;
import com.playground.app.registration.ui.model.UserDisplayInfo;

public interface UserRepository {

    void login(String username, String password, RepositoryCallBack<UserDisplayInfo> callBack);

    void logout();

    Boolean isLoggedIn();

    void getUserDisplayInfo(RepositoryCallBack<UserDisplayInfo> callBack);
}
