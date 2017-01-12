package de.traveltogether.model;

/**
 * Model class for Participant
 * extends Person
 */

public class Participant extends Person {
    private String color = "0xffffffff";
    private double accountBalance = 0.0;
    private String state;

    public String getColor(){
        return color;
    }

    public double getAccountBalance(){
        return accountBalance;
    }

    public String getState(){ return state; }

    public Participant (int _personId, String _name,  String _mailAddress, String color){
        super(_personId, _mailAddress, _name);
        this.color = color;
    }
    public Participant (int _personId, String _name, String color){
        super(_personId, "", _name);
        this.color = color;
    }

    public Participant(int _personId, String _mailAddress, String _name, String _color, double _accountBalance, String _state){
        super(_personId, _mailAddress, _name);
        this.color = _color;
        this.accountBalance=_accountBalance;
        this.state = _state;
    }
}