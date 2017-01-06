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
import de.traveltogether.servercommunication.HashFactory;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;
import de.traveltogether.model.Response;

import static de.traveltogether.servercommunication.HashFactory.hashPassword;


 class LoginInteractor implements ILoginInteractor {
    private ILoginPresenter listener;
    private String hash;

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

    @Override
    public void updateToken(String token, ILoginPresenter _listener) {
        try {
            listener = _listener;
            JSONObject obj = new JSONObject();
            obj.put("deviceId", token);
            obj.put("personId", StaticData.getUserId());

            //TODO: when server ready
            HttpRequest request = new HttpRequest(DataType.PERSON, ActionType.UPDATEDEVICEID, obj.toString(), this);
        }
        catch(Exception e){
            //TODO
        }
    }

    @Override
    public void forgotPassword(String email, ILoginPresenter _listener) {
        listener = _listener;
        try{
            RandomString rs = new RandomString(7);
            String newPW =   rs.nextString();
            String salt = HashFactory.getNextSalt();
            int length = newPW.length();
            char [] newpw = new char[length];
            newPW.getChars(0, length, newpw, 0);
            String newHash = hashPassword(newpw, salt);
            newHash = newHash.replace("/", "");

            JSONObject json = new JSONObject();
            json.put("email", email);
            json.put("plainPassword", newPW);
            json.put("password", newHash);
            json.put("salt", salt.replace("\\u003d", "=").replace("\\n", ""));
            String data = json.toString().replace("\\n", "\n");
            HttpRequest request = new HttpRequest(DataType.PERSON, ActionType.FORGOTPASSWORD, data, this);
        }
        catch (Exception e){
            Log.e(e.getClass().toString(),e.getMessage());
        }
    }

    public void onRequestFinished(Response response, DataType dataType, ActionType actionType){
        if(response.getError().equals("true")){
            listener.onError(response.getMessage());
        }
        else {
            if (dataType == DataType.LOGIN && actionType == ActionType.GETSALT) {
                String salt = "";
                try {
                    JSONObject json = new JSONObject(response.getData());
                    salt = json.get("salt").toString().replace("\\u003d", "=").replace("\\n", "");
                    Log.d("saltParsed", salt);
                } catch (Exception e) {
                    listener.onError("Error in getting salt");
                    Log.d("Error: ", "Error in getting salt");
                }
                if (!salt.equals("")) {
                    listener.onReturnSalt(salt);
                } else {
                    listener.onError("Error in getting salt");
                }
            } else if (dataType == DataType.LOGIN && actionType == ActionType.LOGIN) {
                //save hash in shared prefs
                int userId = -1;
                try {
                    JSONObject json = new JSONObject(response.getData());
                    userId = json.getInt("personId");

                } catch (Exception e) {
                    Log.d("Error: ", e.getMessage());
                }

                SharedPreferences sharedPref = listener.getView().getSharedPreferences("TravelTogetherPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(listener.getView().getString(R.string.saved_hash), hash);
                if (userId != -1) {
                    editor.putInt(listener.getView().getString(R.string.saved_user_id), userId);
                    StaticData.setUserId(userId);
                } else {
                    listener.onError("Fehler beim Anmelden. Bitte versuche es erneut.");//TODO: String Ersetzen
                }
                boolean saved = editor.commit();
                listener.onSuccess(response.getMessage());
            } else if (dataType == DataType.PERSON && actionType == ActionType.UPDATEDEVICEID) {
                Log.d("LoginInteractor", "DeviceId updated");
            }
            else if (dataType == DataType.PERSON && actionType == ActionType.FORGOTPASSWORD){
                listener.onSuccessForgotPassword();
            }
        }
    }

}
