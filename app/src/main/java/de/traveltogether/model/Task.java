package de.traveltogether.model;


/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class Task extends AbstractListObject {
    private String deadline;
    private int personId;
    private int statusId;

    public Task(String _title, int _id, long _tripId, String _description, int _author, String _deadline, int _personId, int _statusId) {
        super(_title, _id,_tripId, _description, _author);
        deadline = _deadline;
        personId = _personId;
        statusId = _statusId;

    }

    public String getDueDate() {
        return deadline;
    }

    public int getPersonId() {
        return personId;
    }

    public int getState() {
        return statusId;
    }

    public void setDueDate(String _deadline) {
         deadline = _deadline;
    }

    public void setPersonId(int _personId) {
        personId = _personId;
    }

    public void setState(int _statusId) {
        statusId = _statusId;
    }

}
