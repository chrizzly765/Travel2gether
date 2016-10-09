package de.traveltogether.notification;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.model.Notification;
import de.traveltogether.model.Response;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.triplist.TripListActivity;

public class NotificationActivity extends AppCompatActivity {

    INotificationPresenter presenter;
    Notification[] notifications;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Benachrichtigungen");
        presenter= new NotificationPresenter(this);
        setContentView(R.layout.activity_notification);
        presenter.onGetNotificationList();
        progressDialog = ProgressDialog.show(this, "",
                "Benachrichtigungen werden geladen...", true);
    }

    void onViewNotificationList(Notification[] _notifications){
        progressDialog.cancel();
        this.notifications = _notifications;

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        NotificationFragment fragment = NotificationFragment.newInstance(_notifications, this);
        fragmentTransaction.add(R.id.activity_notification_fragment_container, fragment);
        fragmentTransaction.commit();
    }

    void onViewErrorMessage(String message){
        progressDialog.cancel();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(getString(R.string.error));
        builder.setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, TripListActivity.class);
        startActivity(intent);
        finish();
    }

}
