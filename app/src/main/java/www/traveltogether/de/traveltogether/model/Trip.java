package www.traveltogether.de.traveltogether.model;

import java.text.SimpleDateFormat;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class Trip {
    private int tripId;
    private String title;
    private String description;
    private String destination;
    private SimpleDateFormat startDate = new SimpleDateFormat("dd.MM.yyyy");
    private SimpleDateFormat endDate = new SimpleDateFormat("dd.MM.yyyy");

}
