package com.playground.app.registration.data.repository;

import android.content.Context;
import android.net.Uri;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.playground.app.registration.data.repository.util.RepositoryCallBack;
import com.playground.app.registration.data.util.Result;
import com.playground.app.registration.ui.model.UserCredentials;
import com.playground.app.registration.ui.model.UserPersonalInfo;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class RegistrationRepositoryImpl implements RegistrationRepository {
    private final FirebaseFirestore firebaseFirestore;
    private final FirebaseAuth firebaseAuth;
    private final FirebaseStorage firebaseStorage;
    private final ExecutorService executorService;
    private final Context context;

    @Inject
    RegistrationRepositoryImpl(
        FirebaseFirestore firebaseFirestore,
        FirebaseAuth firebaseAuth,
        FirebaseStorage firebaseStorage,
        ExecutorService executorService,
        @ApplicationContext Context context
    ) {
        this.firebaseFirestore = firebaseFirestore;
        this.firebaseAuth = firebaseAuth;
        this.firebaseStorage = firebaseStorage;
        this.executorService = executorService;
        this.context = context;
    }

    @Override
    public void registerWithFirebaseAuth(
        UserPersonalInfo userPersonalInfo,
        UserCredentials userCredentials,
        RepositoryCallBack<FirebaseUser> callBack
    ) {
        executorService.execute(() -> {
            try {
                DocumentSnapshot documentSnapshot = Tasks.await(firebaseFirestore.collection("users").document(userCredentials.getUsername().getText()).get());
                if (!documentSnapshot.exists()) {
                    FirebaseUser firebaseUser = Tasks.await(firebaseAuth.createUserWithEmailAndPassword(
                        userPersonalInfo.getEmail().getText(),
                        userCredentials.getPassword().getText())
                    ).getUser();
                    StorageReference storageReference = firebaseStorage.getReference().child("pictures/" + UUID.randomUUID().toString());
                    Tasks.await(storageReference.putFile(userPersonalInfo.getPictureUri()));
                    Uri downloadUri = Tasks.await(storageReference.getDownloadUrl());
                    storageReference.getFile(downloadUri);
                    Tasks.await(firebaseFirestore.collection("users").document(userCredentials.getUsername().getText()).set(createUserExtraInfo(userPersonalInfo, downloadUri)));
                    firebaseAuth.signOut();
                    callBack.onComplete(new Result.Success<>(firebaseUser));
                } else {
                    callBack.onComplete(new Result.Error<>(new Exception("This username already exists")));
                }
            } catch (Exception e) {
                callBack.onComplete(new Result.Error<>(e));
            }
        });
    }

    private HashMap<String, Object> createUserExtraInfo(
        UserPersonalInfo userPersonalInfo,
        Uri downloadUri
    ) {
        return new HashMap<String, Object>() {{
            put("email", userPersonalInfo.getEmail().getText());
            put("firstName", userPersonalInfo.getFirstName().getText());
            put("lastName", userPersonalInfo.getLastName().getText());
            put("phoneNumber", userPersonalInfo.getPhoneNumber().getText());
            put("citizenId", userPersonalInfo.getCitizenId().getText());
            put("pictureUrl", downloadUri);
        }};
    }
}
