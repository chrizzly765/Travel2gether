package de.traveltogether.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class Expense extends AbstractListObject {
    private double amount;
    private int currencyId;
    private int payedBy;
    private List<Payer> payers = new ArrayList<Payer>();

    public double getAmount(){
        return amount;
    }
    public int getPayer(){
        return payedBy;
    }
    public int getCurrencyId(){
        return currencyId;
    }
    public List<Payer> getAssignedPayers(){return payers;}

    public Expense(String _title, int _id, String _description, int _author, double _amount, int _currency, int _payer) {
        super(_title, _id, _description, _author);
        amount = _amount;
        payedBy = _payer;
        currencyId = _currency;
    }

    public void addPayer(int _personId, double _amount){
       payers.add(new Payer(_personId, _amount));
    }
}
