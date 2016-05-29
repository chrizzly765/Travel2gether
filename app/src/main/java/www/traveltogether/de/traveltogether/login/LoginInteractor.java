package www.traveltogether.de.traveltogether.login;

import android.content.Context;
import android.content.SharedPreferences;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import www.traveltogether.de.traveltogether.ActionType;
import www.traveltogether.de.traveltogether.DataType;
import www.traveltogether.de.traveltogether.R;
import www.traveltogether.de.traveltogether.model.Login;
import www.traveltogether.de.traveltogether.servercommunication.HashFactory;
import www.traveltogether.de.traveltogether.servercommunication.HttpRequest;
import www.traveltogether.de.traveltogether.servercommunication.JsonDecode;
import www.traveltogether.de.traveltogether.servercommunication.Response;

import static www.traveltogether.de.traveltogether.servercommunication.HashFactory.hashPassword;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class LoginInteractor implements ILoginInteractor {
    ILoginPresenter listener;
    String hash;

    public void login(String _email, String _hash, ILoginPresenter _listener){
        listener = _listener;
        hash = _hash;
        Login log = new Login(_email, _hash);

        String jsonString = JsonDecode.getInstance().classToJson(log);
        HttpRequest request = new HttpRequest(DataType.LOGIN, ActionType.LOGIN, jsonString, this);
    }

    public void onFinished(Response response){
        if(response.getError()=="true"){
            listener.onError(response.getMessage());
        }
        else {
            //save hash in shared prefs
            SharedPreferences sharedPref = listener.getView().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(listener.getView().getString(R.string.saved_hash), hash.toString());
            editor.commit();

            listener.onSuccess(response.getMessage());
        }
    }

}
