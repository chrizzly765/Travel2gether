package www.traveltogether.de.traveltogether.model;

import android.graphics.Color;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class Participant extends Person {
    private String argbColor = "0xffffffff";
    private double accountBalance = 0.0;
    private String status;

    public String getColor(){
        return argbColor;
    }

    public double getAccountBalance(){
        return accountBalance;
    }

    public Participant (int _personId, String _mailAddress, String _userName){
        super(_personId, _mailAddress, _userName);
    }
}
