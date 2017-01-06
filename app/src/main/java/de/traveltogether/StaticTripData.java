package de.traveltogether;

import java.util.ArrayList;

import de.traveltogether.model.Participant;
import de.traveltogether.participantlist.ParticipantState;

/**
 * Created by Anna-Lena on 09.10.2016.
 */
public class StaticTripData {
    static private Participant[] participants;
    static long currentTripId;
    static private StaticInteractor interactor = new StaticInteractor();

    static public Participant[] getAllParticipants(){
        if(participants==null || participants.length==0){
            return new Participant[0];
        }
        return participants;
    }
    static public void setParticipants(Participant[] _participants){
        participants=_participants;
    }
    static public void setCurrentTripId(long _tripId){
        if(_tripId!=currentTripId) {
            currentTripId = _tripId;
            interactor.getParticipantsForTrip(currentTripId);
        }
    }

    static public Participant[] getActiveParticipants(){
        ArrayList<Participant> activeParticipants = new ArrayList<Participant>();
        if(participants==null || participants.length==0){
            return new Participant[0];
        }
        for(Participant p:participants){
            if(p.getState().equals(ParticipantState.JOINED.toString().toLowerCase())){
                activeParticipants.add(p);
            }
        }
        return toArray(activeParticipants);
    }

    static public Participant[] getInvitedParticipants(){
        ArrayList<Participant> invitedParticipants = new ArrayList<Participant>();
        if(participants==null || participants.length==0){
            return new Participant[0];
        }
        for(Participant p:participants){
            if(p.getState().equals(ParticipantState.INVITED.toString().toLowerCase())){
                invitedParticipants.add(p);
            }
        }
        return toArray(invitedParticipants);
    }

    static public Participant[] getResignedParticipants(){
        ArrayList<Participant> resignedParticipants = new ArrayList<Participant>();
        if(participants==null || participants.length==0){
            return new Participant[0];
        }
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
        if(participants!=null) {
            for (Participant p : participants) {
                if ((int) p.getPersonId() == id) {
                    return p.getUserName();
                }
            }
        }
        return "Not found";
    }


    static public String getColorById(int id){
        if(participants!=null) {
            for (Participant p : participants) {
                if (p.getPersonId() == id) {
                    return p.getColor();
                }
            }
        }
        return "";
    }

    static private Participant[] toArray(ArrayList<Participant> list){
        Participant[] array = new Participant[list.size()];
        for(int i = 0; i<list.size(); i++){
            array[i] = list.get(i);
        }
        return array;
    }
}
