package de.traveltogether.model;

/**
 * Model class for Payer
 */
public class Payer {
    private int personId;
    private double amount;
    public int getId(){return personId;}
    public double getAmount(){return amount;}
    public void setAmount(double _amount){ amount = _amount;}

    public Payer(int _payer){
        personId = _payer;
        amount = 0;
    }
    
    public Payer (int _payer, double _amount){
        personId = _payer;
        amount = _amount;
    }
}
