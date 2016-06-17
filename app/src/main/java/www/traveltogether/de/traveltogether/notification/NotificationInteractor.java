package www.traveltogether.de.traveltogether.notification;

import java.util.ArrayList;
import java.util.List;

import www.traveltogether.de.traveltogether.model.Notification;

/**
 * Created by Anna-Lena on 16.05.2016.
 */
public class NotificationInteractor implements INotificationInteractor {
    private static List<Notification> notifications = new ArrayList<Notification>();


    public static void addNotification(Notification not){
        notifications.add(not);
    }


    public static List<Notification> getNotifications(){
        return notifications;
    }
}
