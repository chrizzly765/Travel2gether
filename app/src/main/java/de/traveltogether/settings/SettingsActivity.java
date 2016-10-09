package de.traveltogether.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import de.traveltogether.R;
import de.traveltogether.login.LoginActivity;
import de.traveltogether.triplist.TripListActivity;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Button logout = (Button)findViewById(R.id.logout_button);
        logout.setOnClickListener(this);

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
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, TripListActivity.class);
        startActivity(intent);
        finish();
    }
}
