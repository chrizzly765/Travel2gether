package www.traveltogether.de.traveltogether.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONObject;

import www.traveltogether.de.traveltogether.ActionType;
import www.traveltogether.de.traveltogether.DataType;
import www.traveltogether.de.traveltogether.R;
import www.traveltogether.de.traveltogether.StaticData;
import www.traveltogether.de.traveltogether.model.Login;
import www.traveltogether.de.traveltogether.servercommunication.HttpRequest;
import www.traveltogether.de.traveltogether.servercommunication.JsonDecode;
import www.traveltogether.de.traveltogether.servercommunication.Response;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class LoginInteractor implements ILoginInteractor, Runnable {
    ILoginPresenter listener;
    String hash;
    Boolean requestForSalt = true;

    public void login(String _email, String _hash, ILoginPresenter _listener){
        listener = _listener;
        hash = _hash;
        Login log = new Login(_email, _hash);

        String jsonString = JsonDecode.getInstance().classToJson(log).replace("\\n", "\n");
        HttpRequest request = new HttpRequest(DataType.LOGIN, ActionType.LOGIN, jsonString, this);
    }

    public void getSalt(String email, ILoginPresenter _listener){
        try {
            listener = _listener;
            JSONObject obj = new JSONObject();
            obj.put("email", email);
            HttpRequest request = new HttpRequest(DataType.LOGIN, ActionType.GETSALT, obj.toString(), this);
        }
        catch(Exception e){
            //TODO
        }

    }

    public void onRequestFinished(Response response){
        if(response.getError()=="true"){
            listener.onError(response.getMessage());
            requestForSalt = true;
        }
        else {
            if(requestForSalt) {
                String salt = "";
                try {
                    JSONObject json = new JSONObject(response.getData());
                    salt = json.get("salt").toString().replace("\\u003d", "=").replace("\\n", "");
                    Log.d("saltParsed", salt);
                } catch (Exception e) {
                    listener.onError("Error in getting salt");
                    Log.d("Error: ", "Error in getting salt");
                }
                if(salt!="") {
                    listener.onReturnSalt(salt);
                    requestForSalt = false;
                }else{
                    listener.onError("Error in getting salt");
                }
            }
            else {
                //save hash in shared prefs
                String userId = "";

                try {
                    JSONObject json = new JSONObject(response.getData());
                    userId = json.get("personId").toString();
                } catch (Exception e) {
                    //TODO:ask explicitly for personId?
                    Log.d("Error: ", "Error in response");
                    requestForSalt = true;
                }

                SharedPreferences sharedPref = listener.getView().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(listener.getView().getString(R.string.saved_hash), hash.toString());
                if (userId != "") {
                    editor.putString(listener.getView().getString(R.string.saved_user_id), userId);
                    StaticData.setUserId(userId);
                }
                editor.commit();

                listener.onSuccess(response.getMessage());
                requestForSalt = true;
            }
        }
    }

    @Override
    public void run() {

    }
}
