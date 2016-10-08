package de.traveltogether.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class Expense extends AbstractListObject {
    private double amount;
    private int currencyId = 0;
    private int payedBy;
    private List<Payer> payer = new ArrayList<Payer>();

    public double getAmount(){
        return amount;
    }
    public void setAmount(double _amount) {amount = _amount;}
    public int getPayer(){
        return payedBy;
    }
    public void setPayer(int _payedBy) {payedBy = _payedBy;}
    public int getCurrencyId(){
        return currencyId;
    }
    public void setCurrencyId(int _currencyId) {currencyId = _currencyId;}
    public List<Payer> getAssignedPayers(){return payer;}
    public void setAssignedPayers(List<Payer> _payer) {payer = _payer;}

    public Expense(String _title, int _id, long _tripId, String _description, int _author, double _amount, int _payer, int _currency) {
        super(_title, _id, _tripId, _description, _author);
        amount = _amount;
        payedBy = _payer;
        currencyId = _currency;
    }

    public void addPayer(int _personId, double _amount){
       payer.add(new Payer(_personId, _amount));
    }
}
