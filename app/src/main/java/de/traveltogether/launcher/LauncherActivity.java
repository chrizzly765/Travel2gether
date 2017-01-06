package de.traveltogether.launcher;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.gcm.GCMRegistrationIntentService;
import de.traveltogether.login.ILoginPresenter;
import de.traveltogether.login.LoginActivity;
import de.traveltogether.login.LoginPresenter;
import de.traveltogether.notification.NotificationActivity;
import de.traveltogether.triplist.TripListActivity;

import static de.traveltogether.servercommunication.HashFactory.hashPassword;

public class LauncherActivity extends AppCompatActivity {

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private IGCMPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new GCMPresenter(this);
        setContentView(R.layout.activity_launcher);
        Log.d("Launcher","start");

        SharedPreferences sharedPref = getSharedPreferences("TravelTogetherPrefs",Context.MODE_PRIVATE );
        //SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String hash = "";
        hash = sharedPref.getString(getString(R.string.saved_hash), "");
        if(hash != ""){
            int userId;
            /*SharedPreferences.Editor editor = sharedPref.edit();
            editor.remove(getString(R.string.saved_user_id));
            editor.commit();*/
            userId = sharedPref.getInt(getString(R.string.saved_user_id), -1);
            if(userId != -1){
                StaticData.setUserId(userId);
                mRegistrationBroadcastReceiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        //Check type of intent filter
                        if(intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_SUCCESS)){
                            //Registration success
                            String token = intent.getStringExtra("token");
                            presenter.onUpdateToken(token);
                        } else if(intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_ERROR)){
                            //Registration error
                            Log.d("Error", "Error in receiving token from GCM.");
                        }
                    }
                };
                try {
                    Uri data= getIntent().getData();
                    long tripId = Long.valueOf(data.getQueryParameter("tripId"));
                    int author = Integer.valueOf(data.getQueryParameter("author"));
                    presenter.onSendInvitation(tripId, author);
                    Intent intent = new Intent(this, NotificationActivity.class);
                    startActivity(intent);
                    finish();
                    Log.d("launcher", "sent invitation");
                    return;
                }
                catch (Exception e){
                    Log.d("Launcher", "No intent data");
                }
                Intent tl = new Intent(this, TripListActivity.class);
                startActivity(tl);
            }
            else{
                Log.d("no userid available", "");
                Intent login = new Intent(this, LoginActivity.class);
                startActivity(login);
            }
        }
        else{
            Log.d("no hash available", hash);
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
        }
        //progressDialog.cancel();
    }
}
