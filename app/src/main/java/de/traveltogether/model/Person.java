package de.traveltogether.model;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class Person {
    private int personId;
    private String mailAddress;
    private String userName;
    public Person(int _personId,String _mailAddress, String _userName){
        personId = _personId;
        mailAddress = _mailAddress;
        userName = _userName;
    }

    public int getPersonId(){
        return personId;
    }

    public String getUserName(){
        return userName;
    }
}
