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
            if(p.getState()== ParticipantState.JOINED.toString().toLowerCase()){
                activeParticipants.add(p);
            }
        }
        return (Participant[]) activeParticipants.toArray();
    }

    static public Participant[] getInvitedParticipants(){
        ArrayList<Participant> invitedParticipants = new ArrayList<Participant>();
        for(Participant p:participants){
            if(p.getState()== ParticipantState.INVITED.toString().toLowerCase()){
                invitedParticipants.add(p);
            }
        }
        return (Participant[]) invitedParticipants.toArray();
    }

    static public Participant[] getResignedParticipants(){
        ArrayList<Participant> resignedParticipants = new ArrayList<Participant>();
        for(Participant p:participants){
            if(p.getState()== ParticipantState.RESIGNED.toString().toLowerCase()){
                resignedParticipants.add(p);
            }
        }
        return (Participant[]) resignedParticipants.toArray();
    }

    /**
     * returns name if id is available, null else
     * @param id
     * @return
     */
    static public String getNameById(long id) {
        for (Participant p : participants) {
            if (p.getPersonId() == id) {
                return p.getUserName();
            }
        }
        return null;
    }
}
