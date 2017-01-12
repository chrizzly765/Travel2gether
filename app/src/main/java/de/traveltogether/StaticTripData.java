package de.traveltogether;

import java.util.ArrayList;

import de.traveltogether.model.Participant;
import de.traveltogether.participantlist.ParticipantState;

/**
 * Static class that provides data of the current chosen trip
 */
public class StaticTripData {
    static private Participant[] participants;
    private static long currentTripId;
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

    /**
     * Returns all active Participants for current trip
     * @return array with active participants
     */
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

    /**
     * Returns all invited Participants for current trip
     * @return array with invited participants
     */
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

    /**
     * Returns all resigned Participants for current trip
     * @return array with resigned participants
     */
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
     * @param id: PersonId
     * @return name
     */
    static public String getNameById(int id) {
        if(participants!=null) {
            for (Participant p : participants) {
                if (p.getPersonId() == id) {
                    return p.getUserName();
                }
            }
        }
        return "Not found";
    }


    /**
     * Returns color if id is available, empty string else
     * @param id: PersonId
     * @return color
     */
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

    /**
     * Converts Participant List to Array
     * @param list
     * @return array with participants in list
     */
    static private Participant[] toArray(ArrayList<Participant> list){
        Participant[] array = new Participant[list.size()];
        for(int i = 0; i<list.size(); i++){
            array[i] = list.get(i);
        }
        return array;
    }
}
