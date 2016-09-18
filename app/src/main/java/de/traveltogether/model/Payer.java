package de.traveltogether.model;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class Payer {
    private int payer;
    private double amount;

    public int getId(){return payer;}
    public double getAmount(){return amount;}


    public Payer(int _payer){
        payer = _payer;
        amount = 0;
    }
    
    public Payer (int _payer, double _amount){
        payer = _payer;
        amount = _amount;
    }
}
