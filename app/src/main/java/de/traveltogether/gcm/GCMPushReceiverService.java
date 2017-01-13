package de.traveltogether.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONObject;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.chat.ChatActivity;
import de.traveltogether.model.Notification;
import de.traveltogether.notification.NotificationActivity;
import de.traveltogether.servercommunication.HttpRequest;

/**
 * GcmListenerService to receive Notifications
 * Extends GcmListenerService
 */
public class GCMPushReceiverService extends GcmListenerService{
    Notification notification;
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String type = data.getString("type");
        String id = data.getString("id");
        String date = data.getString("date");
        String message = data.getString("message");
        if(type.equals(DataType.CHAT.toString())){
            if(StaticData.currentActivity!=null && StaticData.currentActivity.getClass() == ChatActivity.class) {
                ((ChatActivity) StaticData.currentActivity).update();
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("notificationId", id);
                    HttpRequest req = new HttpRequest(DataType.NOTIFICATION, ActionType.UPDATE, obj.toString(), null);
                } catch (Exception e) {
                    Log.e(e.getClass().toString(), e.getMessage());
                }
            }
            else {
                sendNotification(message);
            }
        }
        else {
            sendNotification(message);
        }
    }
    private void sendNotification(String message) {
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int requestCode = 0;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("TravelTogether")
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, noBuilder.build());
    }
}
