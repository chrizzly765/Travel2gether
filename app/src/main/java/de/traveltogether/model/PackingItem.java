package de.traveltogether.model;

/**
 * Model class for PackingItem
 */
public class PackingItem {
    private long id;
    private int personId;
    private boolean status = false;
    private int number = 1;

    public boolean getStatus(){
        return status;
    }
    public void assignToParticipant(int _person){
        personId = _person;
    }
    public int getNumber(){return number;}
    public void setId(long _id){ id=_id; }
    public void toggleStatus(){
        status = !status;
    }
    public int getAssignedPerson(){
        return personId;
    }

    public PackingItem(int _personAssigned) {
        personId = _personAssigned;
        number = 1;
        id = -1;
        status = false;
    }

    public  PackingItem(int _personAssigned, boolean _status, long _id, int _number){
        id=_id;
        personId = _personAssigned;
        status = _status;
        number = _number;
    }

}
