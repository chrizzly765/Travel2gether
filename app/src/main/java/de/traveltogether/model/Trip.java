package de.traveltogether.model;

import de.traveltogether.StaticData;

/**
 * Model class for Trip
 */
public class Trip {
    private Long tripId;
    private String title;
    private String destination;
    private String description;
    private int adminId;
    private int authorId;
    private String startDate;
    private String endDate;
    private int lastUpdateBy;
    private String lastUpdate;

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

    public int getLastUpdateBy(){
        return lastUpdateBy;
    }

    public String getLastUpdate(){ return lastUpdate; }

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

    public Trip(long _tripId, String _title, String _description, String _destination, String _startDate, String _endDate, int _authorId, int _adminId, int _lastUpdateBy, String _lastUpdate){
        tripId = _tripId;
        title = _title;
        description = _description;
        destination = _destination;
        startDate = _startDate;
        endDate = _endDate;
        authorId = _authorId;
        adminId = _adminId;
        lastUpdateBy = _lastUpdateBy;
        lastUpdate=_lastUpdate;
    }
}
