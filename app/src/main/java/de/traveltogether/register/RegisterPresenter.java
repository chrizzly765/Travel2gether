package de.traveltogether.register;

import android.util.Log;

class RegisterPresenter implements IRegisterPresenter {
    private RegisterActivity registerActivity;
    private IRegisterInteractor registerInteractor;

    RegisterPresenter(RegisterActivity _registerActivity){
        registerActivity = _registerActivity;
        registerInteractor = new RegisterInteractor();
    }

    @Override
    public void onRegister(String name, String email, String password, String salt, String token) {
        Log.d("Presenter", "register");
        registerInteractor.register(name, email, password, salt, token, this);

    }

    public void onError(String message){

        try {
            registerActivity.onViewErrorMessage(message);
        }
        catch(Exception e){
            Log.e(e.getClass().toString(), e.getMessage());
        }
    }

    public void onSuccess(String message){

        try {
            registerActivity.onViewSuccessMessage(message);
        }
        catch(Exception e){
            Log.e(e.getClass().toString(), e.getMessage());
        }
    }
}
