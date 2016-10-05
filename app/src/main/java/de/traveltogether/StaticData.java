package de.traveltogether;

import android.util.Log;

import java.util.ArrayList;

import de.traveltogether.model.Participant;
import de.traveltogether.participantlist.ParticipantState;

/**
 * Created by Anna-Lena on 29.05.2016.
 */
public class StaticData {
    static private int UserId;
    static private Participant[] participants;
    //static Long CurrentTripId;

    static public int getUserId() {
        return UserId;
    }
    static public void setUserId(int id){
        UserId = id;
    }

    static public Participant[] getAllParticipants(){
        return participants;
    }

    static public void setParticipants(Participant[] _participants){
        participants=_participants;
    }

    static public Participant[] getActiveParticipants(){
        ArrayList<Participant> activeParticipants = new ArrayList<Participant>();
        for(Participant p:participants){
            if(p.getState().equals(ParticipantState.JOINED.toString().toLowerCase())){
                activeParticipants.add(p);
            }
        }
        return toArray(activeParticipants);
    }

    static public Participant[] getInvitedParticipants(){
        ArrayList<Participant> invitedParticipants = new ArrayList<Participant>();
        for(Participant p:participants){
            if(p.getState().equals(ParticipantState.INVITED.toString().toLowerCase())){
                invitedParticipants.add(p);
            }
        }
        return toArray(invitedParticipants);
    }

    static public Participant[] getResignedParticipants(){
        ArrayList<Participant> resignedParticipants = new ArrayList<Participant>();
        for(Participant p:participants){
            if(p.getState().equals(ParticipantState.RESIGNED.toString().toLowerCase())){
                resignedParticipants.add(p);
            }
        }
        return (toArray(resignedParticipants));
    }

    /**
     * returns name if id is available, null else
     * @param id
     * @return
     */
    static public String getNameById(int id) {
        for (Participant p : participants) {
            if ((int)p.getPersonId() == id) {
                return p.getUserName();
            }
        }
        return "Not found";
    }


    static public String getColorById(int id){
        for (Participant p:participants){
            if(p.getPersonId() == id){
                return p.getColor();
            }
        }
        return null;
    }

    static private Participant[] toArray(ArrayList<Participant> list){
        Participant[] array = new Participant[list.size()];
        for(int i = 0; i<list.size(); i++){
            array[i] = list.get(i);
        }
        return array;
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
