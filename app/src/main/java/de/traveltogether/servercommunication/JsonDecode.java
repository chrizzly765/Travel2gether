package de.traveltogether.servercommunication;
import android.util.Log;

import com.google.gson.Gson;

import de.traveltogether.DataType;


public class JsonDecode {
    private static JsonDecode instance;
    private Gson gson;

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
     * Converts Json String into class
     * @param str json string
     * @param type DataType which has same name as class in which it should be converted
     * @param <T> the resulting object type
     * @return object of type t
     */
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
    public <T> Object jsonToArray(String str, Class<T> cl) {
        //Class<T> arrayclass = (Class<T>)ObjectArray.class;
        T t = null;
        try {
            t = gson.fromJson(str, cl);
        } catch (Exception e) {
            Log.d("Error in json to array", e.getMessage());
        }
        return t;
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
