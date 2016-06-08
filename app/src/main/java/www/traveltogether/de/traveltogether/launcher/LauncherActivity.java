package www.traveltogether.de.traveltogether.launcher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import www.traveltogether.de.traveltogether.R;
import www.traveltogether.de.traveltogether.login.LoginActivity;
import www.traveltogether.de.traveltogether.triplist.TripListActivity;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String hash = "";
        sharedPref.getString(getString(R.string.saved_hash), hash);
        if(hash != ""){
            Intent tl = new Intent(this, TripListActivity.class);
            startActivity(tl);
            //TODO: get userID from shared Prefs
        }
        else{
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
        }
    }
}
