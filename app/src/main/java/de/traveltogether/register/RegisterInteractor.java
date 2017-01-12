package de.traveltogether.register;

import android.util.Log;

import java.util.Objects;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.model.Registration;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;
import de.traveltogether.model.Response;

/**
 * Interactor for RegisterActivity
 */
public class RegisterInteractor implements IRegisterInteractor{

    private IRegisterPresenter listener;

    @Override
    public void register(String name, String email, String password, String salt, String token, IRegisterPresenter _listener) {
        listener = _listener;
        Registration reg = new Registration(name, email, password, salt, token);
        String jsonString = JsonDecode.getInstance().classToJson(reg).replace("\\n", "\n");
        HttpRequest request = new HttpRequest(DataType.REGISTRATION, ActionType.REGISTER, jsonString, this);
    }

    public void onRequestFinished(Response response, DataType dataType, ActionType actionType){
        if(response.getError().equals("true")){
            listener.onError(response.getMessage());
        }
        else {
            listener.onSuccess(response.getMessage());
        }
    }
}
