package com.example.arckpaper.data;

import android.content.Context;
import android.util.Log;

import com.example.arckpaper.R;
import com.example.arckpaper.models.User;
import com.google.firebase.auth.FirebaseAuth;

public class Firebase {
    private final Runnable messageSuccess;
    private final Runnable failure;
    private final Context context;

    public Firebase(Context context, Runnable messageSuccess, Runnable failure) {
        this.messageSuccess = messageSuccess;
        this.failure = failure;
        this.context = context;
    }
    public void loginWithUser(User user) {
        FirebaseAuth instanceFirebase = FirebaseAuth.getInstance();
        instanceFirebase.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        messageSuccess.run();
                        return;
                    }
                    Log.e("LOGIN", context.getString(R.string.firebase_login), task.getException());
                    failure.run();
                });
    }
    public void registerUser(User user) {
        FirebaseAuth instanceFirebase = FirebaseAuth.getInstance();
        instanceFirebase.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        messageSuccess.run();
                        return;
                    }
                    Log.e("REGISTER", context.getString(R.string.firebase_register), task.getException());
                    failure.run();
                });
    }
}
