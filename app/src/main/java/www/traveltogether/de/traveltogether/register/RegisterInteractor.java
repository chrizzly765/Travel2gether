package www.traveltogether.de.traveltogether.register;

import android.util.Log;

import www.traveltogether.de.traveltogether.ActionType;
import www.traveltogether.de.traveltogether.DataType;
import www.traveltogether.de.traveltogether.model.Registration;
import www.traveltogether.de.traveltogether.servercommunication.HttpRequest;
import www.traveltogether.de.traveltogether.servercommunication.JsonDecode;
import www.traveltogether.de.traveltogether.model.Response;

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
