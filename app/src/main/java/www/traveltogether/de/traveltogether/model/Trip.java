package www.traveltogether.de.traveltogether.model;

import java.text.SimpleDateFormat;

import www.traveltogether.de.traveltogether.StaticData;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class Trip {
    private int tripId;
    private String title;
    private String description;
    private String destination;
    //private SimpleDateFormat startDate = new SimpleDateFormat("dd.MM.yyyy");
    private String startDate;
    private String endDate;
    private String autorId;
    //private SimpleDateFormat endDate = new SimpleDateFormat("dd.MM.yyyy");

    public Trip(String _title, String _description, String _destination, String _startDate, String _endDate){
        title = _title;
        description = _description;
        destination = _destination;
        startDate = _startDate;
        endDate = _endDate;
        autorId = StaticData.getUserId();
    }

}
