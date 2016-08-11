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
public class GCMPushReceiverService extends GcmListenerService implements IInteractor, DialogInterface.OnClickListener {
    Notification notification;
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String type = data.getString("type");
        String id = data.getString("id");
        String date = data.getString("date");
        String message = data.getString("message");
        notification = new Notification(message, DataType.valueOf(type), date, Long.valueOf(id));
        //Notification not = new Notification(message, DataType.valueOf(feature_type.toUpperCase()), date, id);
        //NotificationInteractor.addNotification(not);


        //sollte die Einladung anzeigen wenn App geöffnet wird.
        //Wenn das nicht funktioniert, dann erst anzeigen wenn die Notification angetippt wird.

        if(type==DataType.INVITATION.toString().toLowerCase()){
            invite();
            sendNotification(message);
        }
        else if (type==DataType.CHAT.toString().toLowerCase()){
            ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            componentInfo.getPackageName();
        }
        else {
            sendNotification(message);
        }
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
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, noBuilder.build()); //0 = ID of notification
    }

    void invite(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(notification.getMessage());
        builder.setTitle(getString(R.string.invitation));
        builder.setNegativeButton(getString(R.string.decline), this);
        builder.setPositiveButton(getString(R.string.join), this);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {

    }

    /**
     * OnClicklistener for Dialog which appears if notification type was invitation
     * @param dialog
     * @param which
     */
    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which==0){
            dialog.cancel();
            try {
                JSONObject obj = new JSONObject();
                obj.put("tripId", notification.getId().toString());
                obj.put("personId", StaticData.getUserId());
                HttpRequest req = new HttpRequest(DataType.INVITATION, ActionType.DECLINE, obj.toString(), this);
            }
            catch(Exception e){
                Log.e(e.getClass().toString(), e.getMessage());
            }
            dialog.cancel();
        }
        else if(which==1){
            try {
                JSONObject obj = new JSONObject();
                obj.put("tripId", notification.getId().toString());
                obj.put("personId", StaticData.getUserId());
                HttpRequest req = new HttpRequest(DataType.INVITATION, ActionType.ACCEPT, obj.toString(), this);
            }
            catch(Exception e){
                Log.e(e.getClass().toString(), e.getMessage());
            }
            dialog.cancel();
        }
    }
}
