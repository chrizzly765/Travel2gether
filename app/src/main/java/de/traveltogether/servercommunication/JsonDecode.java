package de.traveltogether.servercommunication;
import android.util.Log;

import com.google.gson.Gson;

import de.traveltogether.DataType;

/**
 * Class for decoding objects to json and reverse using Gson
 */
public class JsonDecode {
    private static JsonDecode instance;
    private Gson gson; //Gson object used for convertions

    /**
     * Singelton for JsonDecode class.
     * Use this static object instead of creating own object of the class.
     * @return
     */
    public static JsonDecode getInstance(){
        if(instance ==null){
            instance = new JsonDecode();
        }
        return instance;
    }

    private JsonDecode() {
        gson = new Gson();
    }

    /**
     * Converts Json String into class of DataType type
     * @param str json string
     * @param type DataType which has same name as class it should be converted to
     * @param <T> the resulting object type
     * @return object of type t
     */
    public <T> T jsonToClassByType(String str, DataType type){

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

    /**
     * Converts json string to object of class T
     * @param str json string
     * @param cl class it should be converted to
     * @param <T> the resulting object type
     * @return object of type t
     */
    public <T> Object jsonToClass(String str, Class<T> cl) {
        T t = null;
        try {
            t = gson.fromJson(str, cl);
        } catch (Exception e) {
            Log.d("Error in json to array", e.getMessage());
        }
        return t;
    }

    /**
     * Converts an object to a json string
     * @param obj object that should be converted
     * @return json string
     */
    public String classToJson(Object obj){
        String str = gson.toJson(obj);
        return str;
    }

    /**
     * Converts normal String to json string
     * @param string
     * @return
     */
    public String getJson(String string){
        return gson.toJson(string);
    }

}
