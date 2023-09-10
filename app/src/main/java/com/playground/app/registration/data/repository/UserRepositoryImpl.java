package com.playground.app.registration.data.repository;

import android.net.Uri;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.playground.app.registration.data.repository.util.RepositoryCallBack;
import com.playground.app.registration.data.util.Result;
import com.playground.app.registration.ui.model.UserDisplayInfo;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

public class UserRepositoryImpl implements UserRepository {

    private final FirebaseFirestore firebaseFirestore;
    private final FirebaseAuth firebaseAuth;
    private final ExecutorService executorService;

    @Inject
    UserRepositoryImpl(FirebaseFirestore firebaseFirestore, FirebaseAuth firebaseAuth, ExecutorService executorService) {
        this.firebaseFirestore = firebaseFirestore;
        this.firebaseAuth = firebaseAuth;
        this.executorService = executorService;
    }

    @Override
    public void login(
        String username,
        String password,
        RepositoryCallBack<UserDisplayInfo> callBack
    ) {
        executorService.execute(() -> {
            try {
                DocumentSnapshot documentSnapshot = Tasks.await(firebaseFirestore.collection("users").document(username).get());
                if (documentSnapshot.exists()) {
                    String email = documentSnapshot.getString("email");
                    if (email != null) {
                        FirebaseUser firebaseUser = Tasks.await(firebaseAuth.signInWithEmailAndPassword(
                                email,
                                password
                            )
                        ).getUser();
                        if (firebaseUser != null) {
                            callBack.onComplete(new Result.Success<>(mapToUserDisplayInfo(documentSnapshot)));
                        } else {
                            callBack.onComplete(new Result.Error<>(new Exception("unknown error")));
                        }
                    } else {
                        callBack.onComplete(new Result.Error<>(new Exception("This account has no email")));
                    }
                } else {
                    callBack.onComplete(new Result.Error<>(new Exception("This username is not registered")));
                }
            } catch (Exception e) {
                callBack.onComplete(new Result.Error<>(e));
            }
        });
    }

    @Override
    public void logout() {
        firebaseAuth.signOut();
    }

    @Override
    public Boolean isLoggedIn() {
        return firebaseAuth.getCurrentUser() != null;
    }

    @Override
    public void getUserDisplayInfo(RepositoryCallBack<UserDisplayInfo> callBack) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            executorService.execute(() -> {
                String email = firebaseUser.getEmail();
                try {
                    QuerySnapshot querySnapshot = Tasks.await(firebaseFirestore.collection("users").whereEqualTo("email", email).get());
                    DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                    if (documentSnapshot.exists()) {
                        callBack.onComplete(new Result.Success<>(mapToUserDisplayInfo(documentSnapshot)));
                    } else {
                        callBack.onComplete(new Result.Error<>(new Exception("Unknown error")));
                    }
                } catch (Exception e) {
                    callBack.onComplete(new Result.Error<>(e));
                }
            });
        } else {
            callBack.onComplete(new Result.Error<>(new Exception("Please login")));
        }
    }

    private UserDisplayInfo mapToUserDisplayInfo(DocumentSnapshot documentSnapshot) {
        return new UserDisplayInfo(
            documentSnapshot.getString("email"),
            documentSnapshot.getString("firstName"),
            documentSnapshot.getString("lastName"),
            documentSnapshot.getString("phoneNumber"),
            documentSnapshot.getString("citizenId"),
            Uri.parse(documentSnapshot.getString("pictureUrl"))
        );
    }
}
