package de.traveltogether.gcm;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONObject;

import java.util.List;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.IInteractor;
import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.model.Notification;
import de.traveltogether.model.Response;
import de.traveltogether.notification.NotificationActivity;
import de.traveltogether.notification.NotificationInteractor;
import de.traveltogether.servercommunication.HttpRequest;

/**
 * Created by NgocTri on 4/9/2016.
 */
public class GCMPushReceiverService extends GcmListenerService{
    Notification notification;
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String type = data.getString("type");
        String id = data.getString("id");
        String date = data.getString("date");
        String message = data.getString("message");
        sendNotification(message);
    }
    private void sendNotification(String message) {
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int requestCode = 0;//Your request code
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);
        //Setup notification
        //Sound
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //Build notification
        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("TravelTogether")
                .setContentText(message)
                .setAutoCancel(true)
                .setColor(getColor(R.color.colorPrimary))
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, noBuilder.build()); //0 = ID of notification
    }
}
