package de.traveltogether.settings.profilesettings;

import android.util.Log;

import org.json.JSONObject;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.StaticData;
import de.traveltogether.model.Person;
import de.traveltogether.model.Response;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;

/**
 * Created by Anna-Lena on 27.11.2016.
 */

public class ProfileSettingsInteractor implements IProfileSettingsInteractor {
    IProfileSettingsPresenter listener;

    public void getProfileInfos(IProfileSettingsPresenter _listener) {
        listener = _listener;
        try {
            JSONObject obj = new JSONObject();
            obj.put("personId", StaticData.getUserId());
            HttpRequest request = new HttpRequest(DataType.PERSON, ActionType.DETAIL, obj.toString(), this);
        } catch (Exception e) {
            //TODO
        }
    }

    public void updateProfileInfos(Person person, IProfileSettingsPresenter _listener) {
        listener = _listener;
        try {
            String json = JsonDecode.getInstance().classToJson(person);

            HttpRequest request = new HttpRequest(DataType.PERSON, ActionType.UPDATE, json, this);
        } catch (Exception e) {
            //TODO
        }
    }


    public void changePasswort(String salt, String hash, IProfileSettingsPresenter _listener) {
        listener = _listener;
        try {
            JSONObject obj = new JSONObject();
            obj.put("personId", StaticData.getUserId());
            obj.put("salt", salt);
            obj.put("password", hash);
            HttpRequest request = new HttpRequest(DataType.PERSON, ActionType.UPDATEPASSWORT, obj.toString(), this);
        } catch (Exception e) {
            //TODO
        }
    }

    public void getSalt(String email, IProfileSettingsPresenter _listener){
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
    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        if (response.getError() == "false") {
            if (actionType == ActionType.DETAIL) {
                listener.onSuccessGetProfileInfos((Person) JsonDecode.getInstance().jsonToClass(response.getData(), DataType.PERSON));
            } else if (actionType == ActionType.UPDATE) {
                listener.onSuccessUpdateProfileInfos();

            } else if (actionType == ActionType.UPDATEPASSWORT) {
                listener.onSuccessUpdatePasswort();
            }else if(actionType == ActionType.GETSALT){
                String salt = "";
                try {
                    JSONObject json = new JSONObject(response.getData());
                    salt = json.get("salt").toString().replace("\\u003d", "=").replace("\\n", "");
                    Log.d("saltParsed", salt);
                } catch (Exception e) {
                    listener.onError("Error in getting salt");
                    Log.d("Error: ", "Error in getting salt");
                }
                if (salt != "") {
                    listener.onReturnSalt(salt);
                } else {
                    listener.onError("Error in getting salt");
                }            }
        }else {
            listener.onError(response.getMessage());
        }
    }
}
