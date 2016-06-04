package www.traveltogether.de.traveltogether.model;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class Payer {
    private Participant payer;
    private double amount;

    public Payer (Participant _payer, double _amount){
        payer = _payer;
        amount = _amount;
    }
}
