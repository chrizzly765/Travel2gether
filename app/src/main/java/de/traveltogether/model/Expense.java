package de.traveltogether.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class Expense extends AbstractListObject {
    private double amount;
    private String paidBy;
    private List<Payer> assignedPayers = new ArrayList<Payer>();

    public double getAmount(){
        return amount;
    }
    public String getPayer(){
        return paidBy;
    }

    public Expense(String _title, int _id, String _description, int _author, double _amount, String _payer) {
        super(_title, _id, _description, _author);
        amount = _amount;
        paidBy = _payer;
    }

    public void addPayer(String _personId, double _amount){
       assignedPayers.add(new Payer(_personId, _amount));
    }
}
