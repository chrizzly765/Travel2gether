package de.traveltogether.model;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class Task extends AbstractListObject {

    private Long taskId;
    private int commentCount;
    private String toDoTillDate;
    private Participant traveller;
    private int[] participantIds;


    public Task(String _title, int _id, String _description, int _author) {
        super(_title, _id, _description, _author);
        //toDoTillDate = (_toDoTillDate == "") ? "0000-00-00" : _toDoTillDate;
    }

    public Long getTaskId(){
        return taskId;
    }

    public String getDueDate() {
        return toDoTillDate;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int _commentCount) {
        commentCount = _commentCount;
    }


}
