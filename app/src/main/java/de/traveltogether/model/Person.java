package de.traveltogether.model;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class Person {
    private int personId;
    private String mailAddress = "";
    private String name;

    public Person(int _personId,String _mailAddress, String _userName){
        personId = _personId;
        mailAddress = _mailAddress;
        name = _userName;
    }

    public Person(int _personId, String _userName){
        personId = _personId;
        mailAddress = "";
        name = _userName;
    }

    public int getPersonId(){
        return personId;
    }

    public String getUserName(){
        return name;
    }

    public String getMailAddress(){
        return mailAddress;
    }
}
