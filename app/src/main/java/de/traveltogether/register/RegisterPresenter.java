package de.traveltogether.register;

import android.util.Log;

/**
 * Presenter for RegisterActivity
 */
class RegisterPresenter implements IRegisterPresenter {
    private RegisterActivity registerActivity;
    private IRegisterInteractor registerInteractor;

    RegisterPresenter(RegisterActivity _registerActivity){
        registerActivity = _registerActivity;
        registerInteractor = new RegisterInteractor();
    }

    @Override
    public void onRegister(String name, String email, String password, String salt, String token) {
        registerInteractor.register(name, email, password, salt, token, this);
    }

    public void onError(String message){
        registerActivity.onViewErrorMessage(message);
    }

    public void onSuccess(String message){
        registerActivity.onViewSuccessMessage(message);
    }
}
