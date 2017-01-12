package de.traveltogether.options.profilesettings;

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
 * Interactor for ProfileSettingsActivity
 * implements IProfileSettingsInteractor
 */
 class ProfileSettingsInteractor implements IProfileSettingsInteractor {
    private IProfileSettingsPresenter listener;

    public void getProfileInfos(IProfileSettingsPresenter _listener) {
        listener = _listener;
        try {
            JSONObject obj = new JSONObject();
            obj.put("personId", StaticData.getUserId());
            HttpRequest request = new HttpRequest(DataType.PERSON, ActionType.DETAIL, obj.toString(), this);
        } catch (Exception e) {
            listener.onError("Fehler beim Http Request");
            Log.e(e.getClass() + "", e.getMessage());
        }
    }

    public void updateProfileInfos(Person person, IProfileSettingsPresenter _listener) {
        listener = _listener;
        try {
            String json = JsonDecode.getInstance().classToJson(person);

            HttpRequest request = new HttpRequest(DataType.PERSON, ActionType.UPDATE, json, this);
        } catch (Exception e) {
            listener.onError("Fehler beim Http Request");
            Log.e(e.getClass() + "", e.getMessage());
        }
    }


    public void changePasswort(String salt, String hash, IProfileSettingsPresenter _listener) {
        listener = _listener;
        try {
            JSONObject obj = new JSONObject();
            obj.put("personId", StaticData.getUserId());
            obj.put("salt", salt.replace("\\u003d", "=").replace("\\n", ""));
            obj.put("password", hash);
            String s = obj.toString().replace("\\n", "\n").replace("\\/", "/");
            HttpRequest request = new HttpRequest(DataType.PERSON, ActionType.UPDATEPASSWORD, s, this);
        } catch (Exception e) {
            listener.onError("Fehler beim Http Request");
            Log.e(e.getClass() + "", e.getMessage());
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
            listener.onError("Fehler beim Http Request");
            Log.e(e.getClass() + "", e.getMessage());
        }

    }
    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        if (response.getError().equals("false")) {
            if (actionType == ActionType.DETAIL) {
                listener.onSuccessGetProfileInfos((Person) JsonDecode.getInstance().jsonToClassByType(response.getData(), DataType.PERSON));
            } else if (actionType == ActionType.UPDATE) {
                listener.onSuccessUpdateProfileInfos();

            } else if (actionType == ActionType.UPDATEPASSWORD) {
                listener.onSuccessUpdatePassword();
            }else if(actionType == ActionType.GETSALT){
                String salt = "";
                try {
                    JSONObject json = new JSONObject(response.getData());
                    salt = json.get("salt").toString().replace("\\u003d", "=").replace("\\n", "");
                } catch (Exception e) {
                    listener.onError("Error in getting salt");
                }
                if (!salt.equals("")) {
                    listener.onReturnSalt(salt);
                } else {
                    listener.onError("Error in getting salt");
                }
            }
        }else {
            listener.onError(response.getMessage());
        }
    }
}
