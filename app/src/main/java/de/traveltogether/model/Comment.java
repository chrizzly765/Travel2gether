package de.traveltogether.model;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class Comment {
    private long author =-1;
    private String content;
    private String added;

    public long getId(){return author;}
    public String getText(){
        return content;
    }
    public String getDate(){
        return added;
    }

    public Comment(long _author, String _content, String _date){
        author = _author ;
        content=_content;
        added = _date;
    }
}
