package www.traveltogether.de.traveltogether.login;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

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

    public void onLogin(String _email, String _password){
        interactor.login(_email, _password, this);
    }

    public void onSuccess(String message){

    }
    public void onError(String message){

    }
    public AppCompatActivity getView(){
        return view;
    }
}
