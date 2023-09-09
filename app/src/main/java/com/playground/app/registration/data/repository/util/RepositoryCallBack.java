package com.playground.app.registration.data.repository.util;

import com.playground.app.registration.data.model.Result;

public interface RepositoryCallBack<T> {
    void onComplete(Result<T> result);
}