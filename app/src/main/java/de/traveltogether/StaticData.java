package de.traveltogether;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import de.traveltogether.model.Participant;
import de.traveltogether.model.Statistic;
import de.traveltogether.participantlist.ParticipantState;

/**
 * Created by Anna-Lena on 29.05.2016.
 */
public class StaticData {
    static private int UserId;
    static public String currencyFormatDE = "#0.00";
    static public String currencySymbolDE = "€";
    static public AppCompatActivity currentActivity;

    static public int getUserId() {
        return UserId;
    }
    static public void setUserId(int id){
        UserId = id;
    }

    static public int getIdForColor(String color){
        switch(color){
            case "#00c9c5":
                return R.drawable.circle00c9c5;
            case "#00d641":
                return R.drawable.circle00d641;
            case "#00e6b6":
                return R.drawable.circle00e6b6;
            case "#002c8c":
                return R.drawable.circle002c8c;
            case "#002f5b":
                return R.drawable.circle002f5b;
            case "#9bd641":
                return R.drawable.circle9bd641;
            case "#37a9e3":
                return R.drawable.circle37a9e3;
            case "#255fb6":
                return R.drawable.circle255fb6;
            case "#dce641":
                return R.drawable.circledce641;
            case "#ffe154":
                return R.drawable.circleffe154;
            default:
                return R.drawable.circle_light_grey;

        }
    }
}
