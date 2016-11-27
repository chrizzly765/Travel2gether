package de.traveltogether.model;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class PackingItem {
    long id;
    private int personId;
    private boolean status = false;
    //private long id = 0;
    private int number = 1;

    public boolean getStatus(){
        return status;
    }
    public void assignToParticipant(int _person){
        personId = _person;
    }
    //public long getId(){return id;}
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
        //id = 0;
        status = false;
    }

    public  PackingItem(int _personAssigned, boolean _status, long _id, int _number){
        id=_id;
        personId = _personAssigned;
        status = _status;
        number = _number;
    }

}
