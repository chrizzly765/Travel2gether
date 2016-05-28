package www.traveltogether.de.traveltogether.servercommunication;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import www.traveltogether.de.traveltogether.DataType;

/**
 * Created by Anna-Lena on 16.05.2016.
 */
public class JsonDecode implements IJsonParser {
    private static JsonDecode instance;
    Gson gson;

    public static JsonDecode getInstance(){
        if(instance ==null){
            instance = new JsonDecode();
        }
        return instance;
    }

    public JsonDecode() {
        gson = new Gson();
    }

    public Object jsonToClass(String str, DataType type){
        try {
            Object obj = gson.fromJson(str, Class.forName(type.toString()));
            return obj;
        }
        catch(Exception e) {
            Log.d("Error in json to class", e.getMessage());
        }
        return null;
    }

    public String classToJson(Object obj){
        String str = gson.toJson(obj);
        Log.d("class to json", str);
        return str;
    }

    public String getJson(String string){
        return gson.toJson(string);
    }

}
