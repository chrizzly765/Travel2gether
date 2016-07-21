package de.traveltogether.servercommunication;
import android.util.Log;

import com.google.gson.Gson;

import de.traveltogether.DataType;

/**
 * Created by Anna-Lena on 16.05.2016.
 */
public class JsonDecode {
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

    public <T> T jsonToClass(String str, DataType type){

        try {
            T obj = (T)gson.fromJson(str, Class.forName("de.traveltogether.model." + type.toString()));
            return obj;
        }
        catch(ClassNotFoundException e){
            Log.e("Class not found", e.getMessage());
        }
        catch(Exception e) {
            Log.d("Error in json to class", e.getMessage());
        }
        return null;
    }

//    public Object jsonToClass(String str, DataType type){
//
//        try {
//            Object obj = gson.fromJson(str, Class.forName("de.traveltogether.model." + type.toString()));
//            return obj;
//        }
//        catch(ClassNotFoundException e){
//            Log.e("Class not found", e.getMessage());
//        }
//        catch(Exception e) {
//            Log.d("Error in json to class", e.getMessage());
//        }
//        return null;
//    }

    /*public <T> ArrayList<T> jsonToArray(String str, DataType type){
        try {
            String string = str.replace("[", "").replace("]","");
            Log.d("jsonToarray",string);
            String pattern = Pattern.quote("},{");
            String [] array = string.split(pattern);
            if (string != "{}") {
                ArrayList<T> objList = new ArrayList<T>();
                for (int i = 0; i < array.length; i++) {
                    if(!array[i].startsWith("{")) {
                        array[i] = "{" + array[i];
                    }
                    if(!array[i].endsWith("}")){
                        array[i] = array[i] + "}";
                    }
                    Log.d("string", array[i]);
                    objList.add((T)jsonToClass(array[i], type));
                }
                Log.d("JsonDecode", "return");
                return objList;
            }
        }
        catch(Exception e){
            Log.d("Exception jsontoArray: ", e.getMessage());
        }
        return new ArrayList<T>();
    }*/
    public <T> Object jsonToArray(String str, Class<T> cl){
        //Class<T> arrayclass = (Class<T>)ObjectArray.class;

       return gson.fromJson(str,cl);
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
