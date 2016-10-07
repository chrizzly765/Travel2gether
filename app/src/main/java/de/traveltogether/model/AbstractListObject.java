package de.traveltogether.model;

import java.text.SimpleDateFormat;

import de.traveltogether.DataType;
import de.traveltogether.StaticData;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public abstract class AbstractListObject {
    protected String title;
    protected long id;
    protected long tripId;
    protected String description;
    protected String lastUpdate;// = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
    protected String added;// = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
    protected int author;
    protected int lastUpdateBy;
    protected int commentsNumber;
    //protected DataType type; //do we need??

    public String getTitle(){
        return title;
    }

    public long getId(){
        return id;
    }

    public long getTripId(){
        return tripId;
    }

    public String getDescription(){
        return description;
    }

    public String getLastUpdate(){
        return lastUpdate;
    }

    public String getAddedDate(){
        return added;
    }

    public int getAuthor(){
        return author;
    }

    public int getLastChangedPerson(){
        return lastUpdateBy;
    }

    public int getCommentsNumber(){return commentsNumber; }

    public void setTitle(String _title){
        title = _title;
    }

    public void setDescription(String _description){ description = _description; }

    public void setLastUpdateBy(int _lastUpdateBy){ lastUpdateBy = _lastUpdateBy; }

    //TODO: implement description as optional
    public AbstractListObject(String _title, long _id, long _tripId, String _description, int _author){
        title = _title;
        id = _id;
        tripId = _tripId;
        description = _description;
        author = _author;
        lastUpdateBy = StaticData.getUserId();
    }

    public DataType getTypeById(int id){
        DataType type = DataType.TRIP;
        //TODO :get datatype by id
        return type;
    }
}
