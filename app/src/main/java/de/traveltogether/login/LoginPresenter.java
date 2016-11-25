package de.traveltogether.login;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class LoginPresenter implements ILoginPresenter {
    protected LoginActivity view;
    protected ILoginInteractor interactor;
    public LoginPresenter(LoginActivity _view){
        view = _view;
        interactor = new LoginInteractor();
    }

    public void onLogin(String _email, String _password, String _token){
        interactor.login(_email, _password, _token, this);
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
}
