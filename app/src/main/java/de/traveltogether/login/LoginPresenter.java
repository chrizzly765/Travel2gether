package de.traveltogether.login;

import android.support.v7.app.AppCompatActivity;


public class LoginPresenter implements ILoginPresenter {
    protected LoginActivity view;
    protected ILoginInteractor interactor;
    LoginPresenter(LoginActivity _view){
        view = _view;
        interactor = new LoginInteractor();
    }

    public void onLogin(String _email, String _password){
        interactor.login(_email, _password, this);
    }

    public void onSuccess(String message){
        view.onViewSuccess(message);
    }
    public void onError(String message){
        view.onViewError(message);
    }
    public AppCompatActivity getView(){
        return view;
    }
    public void onGetSalt(String email){interactor.getSalt(email, this);}
    public void onReturnSalt(String salt){view.onGetSaltSuccess(salt);}
    public void onUpdateToken(String token){interactor.updateToken(token, this);}

    @Override
    public void onForgotPassword(String email) {
        interactor.forgotPassword(email, this);
    }

    @Override
    public void onSuccessForgotPassword() {
        view.onSuccessForgotPassword();
    }
}
