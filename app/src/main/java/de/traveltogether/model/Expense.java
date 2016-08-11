package de.traveltogether.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class Expense extends AbstractListObject {
    private double amount;
    private Participant payedBy;
    private List<Payer> assignedPayers = new ArrayList<Payer>();

    public double getAmount(){
        return amount;
    }
    public Participant getPayer(){
        return payedBy;
    }

    public Expense(String _title, int _id, String _description, Participant _author, double _amount, Participant _payer) {
        super(_title, _id, _description, _author);
        amount = _amount;
        payedBy = _payer;
    }

    public void addPayer(Participant _person, double _amount){
       assignedPayers.add(new Payer(_person, _amount));
    }
}
