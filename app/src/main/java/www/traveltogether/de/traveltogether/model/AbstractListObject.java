package www.traveltogether.de.traveltogether.model;

import java.text.SimpleDateFormat;

import www.traveltogether.de.traveltogether.DataType;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public abstract class AbstractListObject {
    protected String title;
    protected int id;
    protected String description;
    protected SimpleDateFormat lastChangedDate = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
    protected SimpleDateFormat addedDate = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
    protected Participant author;
    protected Participant lastChangedPerson;
    //protected DataType type; //do we need??

    public String getTitle(){
        return title;
    }

    public int getId(){
        return id;
    }

    public String getDescription(){
        return description;
    }

    public SimpleDateFormat getLastChangedDate(){
        return lastChangedDate;
    }

    public SimpleDateFormat getAddedDate(){
        return addedDate;
    }

    public Participant getAuthor(){
        return author;
    }

    public Participant getLastChangedPerson(){
        return lastChangedPerson;
    }


    //TODO: implement description as optional
    public AbstractListObject(String _title, int _id, String _description, Participant _author){
        title = _title;
        id = _id;
        description = _description;
        author = _author;
    }

    public www.traveltogether.de.traveltogether.DataType getTypeById(int id){
        DataType type = DataType.TRIP;
        //TODO :get datatype by id
        return type;
    }
}
