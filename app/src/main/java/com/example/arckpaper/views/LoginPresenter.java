package com.example.arckpaper.views;

import android.content.Context;

import com.example.arckpaper.models.User;
import com.example.arckpaper.views.interfaces.ILoginView;

public class LoginPresenter {
    private ILoginView view;
    private Context context;

    public LoginPresenter(ILoginView view, Context context) {
        this.view = view;
        this.context = context;
    }
    public void insertUser(User user){
        if(!user.validateUser()){
            view.showError("los campos no estan llenos");
            return;
        }
        view.showInsertUser(user.insertUser(user));
    }
    public void loginFirebase(User user){

    }
}
