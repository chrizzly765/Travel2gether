package www.traveltogether.de.traveltogether.model;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class Person {
    int personId;
    String mailAddress;
    String userName;
    public Person(int _personId,String _mailAddress, String _userName){
        personId = _personId;
        mailAddress = _mailAddress;
        userName = _userName;
    }
}
