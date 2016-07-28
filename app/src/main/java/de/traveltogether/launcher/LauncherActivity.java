package de.traveltogether.launcher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.login.LoginActivity;
import de.traveltogether.triplist.TripListActivity;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        Log.d("Launcher","start");

        SharedPreferences sharedPref = getSharedPreferences("TravelTogetherPrefs",Context.MODE_WORLD_READABLE );
        //SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String hash = "";
        hash = sharedPref.getString(getString(R.string.saved_hash), "");
        if(hash != ""){
            String userId = "";
            userId = sharedPref.getString(getString(R.string.saved_user_id), "");
            if(userId != ""){
                Log.d("UserId available", userId);
                Intent tl = new Intent(this, TripListActivity.class);
                StaticData.setUserId(userId);
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
    }
}
