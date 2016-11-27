package de.traveltogether.settings;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import de.traveltogether.R;
import de.traveltogether.login.LoginActivity;
import de.traveltogether.settings.profilesettings.ProfileSettingsActivity;
import de.traveltogether.triplist.TripListActivity;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Optionen");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setContentView(R.layout.activity_settings);
        LinearLayout logout = (LinearLayout)findViewById(R.id.logout_button);
        LinearLayout profile = (LinearLayout)findViewById(R.id.profile_button);

        logout.setOnClickListener(this);
        profile.setOnClickListener(this);

    }

    public void onClick(View v){
        if(v.getId() == R.id.logout_button){
            SharedPreferences sharedPref = this.getSharedPreferences("TravelTogetherPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.remove(getString(R.string.saved_hash));
            editor.remove(getString(R.string.saved_user_id));
            editor.apply();

            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
            finish();
        }
        else if(v.getId() == R.id.profile_button){
            Intent intent = new Intent(this, ProfileSettingsActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                super.onOptionsItemSelected(item);
                break;
        }
        return  true;
    }
}
