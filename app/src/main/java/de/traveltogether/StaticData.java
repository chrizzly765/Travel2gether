package de.traveltogether;

/**
 * Created by Anna-Lena on 29.05.2016.
 */
public class StaticData {
    static int UserId;
    //static Long CurrentTripId;

    static public int getUserId() {
        return UserId;
    }
    static public void setUserId(int id){
        UserId = id;
    }

}
