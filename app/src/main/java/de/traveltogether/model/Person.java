package de.traveltogether.model;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class Person {
    private int personId;
    private String email = "";
    private String name;

    public Person(int _personId,String _mailAddress, String _userName){
        personId = _personId;
        email = _mailAddress;
        name = _userName;
    }

    public Person(int _personId, String _userName){
        personId = _personId;
        email = "";
        name = _userName;
    }

    public int getPersonId(){
        return personId;
    }

    public String getUserName(){
        return name;
    }

    public String getMailAddress(){
        return email;
    }
}
