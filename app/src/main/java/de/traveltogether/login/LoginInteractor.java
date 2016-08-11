package de.traveltogether.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONObject;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.model.Login;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;
import de.traveltogether.model.Response;

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

    public void onRequestFinished(Response response, DataType dataType, ActionType actionType){
        if(response.getError()=="true"){
            listener.onError(response.getMessage());
        }
        else {
            if(dataType == DataType.LOGIN && actionType == ActionType.GETSALT) {
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
                }else{
                    listener.onError("Error in getting salt");
                }
            }
            else if(dataType ==DataType.LOGIN && actionType == ActionType.LOGIN){
                //save hash in shared prefs
                int userId = -1;

                try {
                    JSONObject json = new JSONObject(response.getData());
                    userId = json.getInt("personId");

                } catch (Exception e) {
                    //TODO:ask explicitly for personId?
                    Log.d("Error: ", e.getMessage());
                }

                SharedPreferences sharedPref =  listener.getView().getSharedPreferences("TravelTogetherPrefs", Context.MODE_WORLD_READABLE);
                //SharedPreferences sharedPref = listener.getView().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(listener.getView().getString(R.string.saved_hash), hash.toString());
                if (userId != -1) {
                    editor.putInt(listener.getView().getString(R.string.saved_user_id), userId);
                    StaticData.setUserId(userId);
                }
                else{
                    listener.onError("Fehler beim Anmelden. Bitte versuche es erneut.");//TODO: String Ersetzen
                }
                //editor.apply();
                boolean saved = editor.commit();
                listener.onSuccess(response.getMessage());
            }
        }
    }

}
