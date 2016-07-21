package de.traveltogether.model;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class PackingItem {
    private Participant assignedPerson = null;
    private boolean packedCheckBox = false;

    public Participant getAssignedPerson(){
        return assignedPerson;
    }

    public boolean getPackedCheckBox(){
        return packedCheckBox;
    }
    public void assignToParticipant(Participant _person){
        assignedPerson = _person;
    }

}
