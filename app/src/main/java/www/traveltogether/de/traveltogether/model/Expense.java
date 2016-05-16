package www.traveltogether.de.traveltogether.model;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class Expense extends AbstractListObject {
    private double amount;
    private Participant payer;

    public Expense(String _title, int _id, String _description, Participant _author, double _amount, Participant _payer) {
        super(_title, _id, _description, _author);
        amount = _amount;
        payer = _payer;
    }
}
