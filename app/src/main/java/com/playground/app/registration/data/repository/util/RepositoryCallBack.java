package com.playground.app.registration.data.repository.util;

import com.playground.app.registration.data.util.Result;

public interface RepositoryCallBack<T> {
    void onComplete(Result<T> result);
}