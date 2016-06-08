package www.traveltogether.de.traveltogether.login;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public interface ILoginPresenter {

    public void onLogin(String email, String password);
    public void onSuccess(String message);
    public void onError(String message);
    public AppCompatActivity getView();
    public void onGetSalt(String email);
    public void onReturnSalt(String salt);
}
