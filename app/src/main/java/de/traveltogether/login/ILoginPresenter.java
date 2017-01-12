package de.traveltogether.login;

import android.support.v7.app.AppCompatActivity;

/**
 * Interface for Presenter of LoginActivity
 */
public interface ILoginPresenter {
     void onLogin(String email, String password);
     void onSuccess(String message);
     void onError(String message);
     AppCompatActivity getView();
     void onGetSalt(String email);
     void onReturnSalt(String salt);
     void onUpdateToken(String token);
     void onForgotPassword(String email);
     void onSuccessForgotPassword();
}
