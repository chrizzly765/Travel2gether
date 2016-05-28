package www.traveltogether.de.traveltogether.register;

import android.util.Log;

import www.traveltogether.de.traveltogether.ActionType;
import www.traveltogether.de.traveltogether.DataType;
import www.traveltogether.de.traveltogether.model.Registration;
import www.traveltogether.de.traveltogether.servercommunication.HttpRequest;
import www.traveltogether.de.traveltogether.servercommunication.JsonDecode;
import www.traveltogether.de.traveltogether.servercommunication.Response;

/**
 * Created by Anna-Lena on 27.05.2016.
 */
public class RegisterInteractor implements IRegisterInteractor {

    //private static final String TAG = RegisterInteractor.class.getSimpleName();
    IRegisterPresenter listener;

    @Override
    public void register(String name, String email, String password, IRegisterPresenter _listener) {
        listener = _listener;
        Registration reg = new Registration(name, email, password);
        Log.d("reg", "register: ");

        String jsonString = JsonDecode.getInstance().classToJson(reg);
        HttpRequest request=new HttpRequest(DataType.REGISTRATION, ActionType.REGISTER, jsonString, this);

    }

    public void onFinished(Response response){
        if(response.getError()=="true"){
            listener.onError(response.getMessage());
        }
        else {
            listener.onSuccess(response.getMessage());
        }
    }

}
