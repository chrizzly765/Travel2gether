package www.traveltogether.de.traveltogether.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import www.traveltogether.de.traveltogether.R;
import www.traveltogether.de.traveltogether.login.LoginActivity;
import www.traveltogether.de.traveltogether.triplist.TripListActivity;

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
            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.remove(getString(R.string.saved_hash));
            editor.remove(getString(R.string.saved_user_id));
            editor.commit();

            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
            finish();
        }
    }
}
