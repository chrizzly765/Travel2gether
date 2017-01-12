package de.traveltogether.model;

import de.traveltogether.DataType;
import de.traveltogether.StaticData;

/**
 * Abstract Base Class for feature
 */
public abstract class AbstractListObject {
    private String title;
    private long id;
    private long tripId;
    private String description;
    private String lastUpdate;// = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
    private String added;// = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
    private int author;
    private int lastUpdateBy;
    private int commentsNumber;

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

    AbstractListObject(String _title, long _id, long _tripId, String _description, int _author){
        title = _title;
        id = _id;
        tripId = _tripId;
        description = _description;
        author = _author;
        lastUpdateBy = StaticData.getUserId();
    }
}
