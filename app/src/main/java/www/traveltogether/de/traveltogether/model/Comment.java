package www.traveltogether.de.traveltogether.model;

import java.text.SimpleDateFormat;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class Comment {
    private Participant author;
    private String text;
    private SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");

    public Participant getAuthor(){
        return author;
    }
    public String getText(){
        return text;
    }
    public SimpleDateFormat getDate(){
        return date;
    }
    public Comment(Participant _author, String _text, SimpleDateFormat _date){
        author= _author;
        text = _text;
        date= _date;
    }
}
