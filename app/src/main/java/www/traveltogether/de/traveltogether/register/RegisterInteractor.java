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

    @Override
    public void register(String name, String email, String password, IRegisterPresenter listener) {
        Registration reg = new Registration(name, email, password);
        Log.d("reg", "register: ");

        String jsonString = JsonDecode.getInstance().classToJson(reg);
        HttpRequest request=new HttpRequest(DataType.REGISTER, ActionType.REGISTER, jsonString);
        Response response = request.getResponse();
        if(response.getStatus()=="error"){
            listener.onError(response.getMessage());
        }
        else {
            listener.onSuccess(response.getMessage());
        }
    }
}
