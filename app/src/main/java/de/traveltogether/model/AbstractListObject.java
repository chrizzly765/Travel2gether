package de.traveltogether.model;

import java.text.SimpleDateFormat;

import de.traveltogether.DataType;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public abstract class AbstractListObject {
    protected String title;
    protected long id;
    protected String description;
    protected String lastChangedDate;// = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
    protected String addedDate;// = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
    protected int authorId;
    protected int lastChangedPersonId;
    //protected DataType type; //do we need??

    public String getTitle(){
        return title;
    }

    public long getId(){
        return id;
    }

    public String getDescription(){
        return description;
    }

    public String getLastChangedDate(){
        return lastChangedDate;
    }

    public String getAddedDate(){
        return addedDate;
    }

    public int getAuthor(){
        return authorId;
    }

    public int getLastChangedPerson(){
        return lastChangedPersonId;
    }


    //TODO: implement description as optional
    public AbstractListObject(String _title, int _id, String _description, int _author){
        title = _title;
        id = _id;
        description = _description;
        authorId = _author;
    }

    public DataType getTypeById(int id){
        DataType type = DataType.TRIP;
        //TODO :get datatype by id
        return type;
    }
}
