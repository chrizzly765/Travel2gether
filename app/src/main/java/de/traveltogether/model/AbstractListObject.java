package de.traveltogether.model;

import de.traveltogether.DataType;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public abstract class AbstractListObject {
    protected String title;
    protected long id;
    protected String description;
    protected String last_update;// = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
    protected String added;// = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
    protected int author;
    protected int last_update_by;
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

    public String getLastUpdate(){
        return last_update;
    }

    public String getAddedDate(){
        return added;
    }

    public int getAuthor(){
        return author;
    }

    public int getLastChangedPerson(){
        return last_update_by;
    }


    //TODO: implement description as optional
    public AbstractListObject(String _title, int _id, String _description, int _author){
        title = _title;
        id = _id;
        description = _description;
        author = _author;
    }

    public DataType getTypeById(int id){
        DataType type = DataType.TRIP;
        //TODO :get datatype by id
        return type;
    }
}
