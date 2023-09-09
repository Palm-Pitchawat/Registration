package com.playground.app.registration.data.repository.di;

import com.playground.app.registration.data.repository.RegistrationRepository;
import com.playground.app.registration.data.repository.RegistrationRepositoryImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {

    @Binds
    @Singleton
    public abstract RegistrationRepository bindsRegistrationRepository(
            RegistrationRepositoryImpl registrationRepositoryImpl
    );
}
