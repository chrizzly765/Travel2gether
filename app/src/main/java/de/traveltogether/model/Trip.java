package de.traveltogether.model;

import de.traveltogether.StaticData;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class Trip {
    private Long tripId;
    private String title;
    private String destination;
    private String description;
    private int adminId;
    private int authorId;
    //private SimpleDateFormat startDate = new SimpleDateFormat("dd.MM.yyyy");
    private String startDate;
    private String endDate;
    //private SimpleDateFormat endDate = new SimpleDateFormat("dd.MM.yyyy");
    private ParticipantShort[] participants;

    public Long getTripId(){
        return tripId;
    }

    public String getTitle(){
        return title;
    }

    public String getDestination(){
        return destination;
    }

    public String getDescription(){
        return description;
    }

    public int getAuthorId(){
        return authorId;
    }

    public String getStartDate(){
        return startDate;
    }

    public String getEndDate(){
        return endDate;
    }

    public int getAdminId(){
        return adminId;
    }

    public ParticipantShort[] getParticipants(){return participants;}

    public Trip(String _title, String _description, String _destination, String _startDate, String _endDate){
        title = _title;
        description = _description;
        destination = _destination;
        startDate = _startDate;
        endDate = _endDate;
        authorId = StaticData.getUserId();
        adminId = authorId;
        participants = new ParticipantShort[0];
    }

}
