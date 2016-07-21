package de.traveltogether.register;

import android.util.Log;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.model.Registration;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;
import de.traveltogether.model.Response;

/**
 * Created by Anna-Lena on 27.05.2016.
 */
public class RegisterInteractor implements IRegisterInteractor, Runnable{

    //private static final String TAG = RegisterInteractor.class.getSimpleName();
    IRegisterPresenter listener;

    @Override
    public void register(String name, String email, String password, String salt, String token, IRegisterPresenter _listener) {
            listener = _listener;
            Registration reg = new Registration(name, email, password, salt, token);
            String jsonString = JsonDecode.getInstance().classToJson(reg).replace("\\n", "\n");
            HttpRequest request = new HttpRequest(DataType.REGISTRATION, ActionType.REGISTER, jsonString, this);
    }

    public void onRequestFinished(Response response, DataType dataType, ActionType actionType){
        if(response.getError()=="true"){
            Log.d("error", "");
            listener.onError(response.getMessage());
        }
        else {
            Log.d("success","");

            listener.onSuccess(response.getMessage());
        }
    }

    @Override
    public void run() {

    }
}
