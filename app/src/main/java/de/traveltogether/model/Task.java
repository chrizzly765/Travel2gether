package de.traveltogether.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class Task extends AbstractListObject {

    private int taskId;
    private int authorId;
    private String todoTill;
    private Participant traveller;
    private List<Participant> assignedParticipants = new ArrayList<>();


    public Task(String _title, int _id, String _description, Participant _author) {
        super(_title, _id, _description, _author);
        taskId = _id;

    }

    public int getTaskId() {
        return taskId;
    }

    public int getAuthorId() {
        return authorId;
    }

}
