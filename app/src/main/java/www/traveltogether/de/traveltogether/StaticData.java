package www.traveltogether.de.traveltogether;

/**
 * Created by Anna-Lena on 29.05.2016.
 */
public class StaticData {
    static String UserId;

    static public String getUserId() {
        return UserId;
    }
    static public void setUserId(String id){
        UserId = id;
    }
}
