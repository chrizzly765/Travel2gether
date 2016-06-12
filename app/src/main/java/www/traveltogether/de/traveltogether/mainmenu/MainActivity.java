package www.traveltogether.de.traveltogether.mainmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import www.traveltogether.de.traveltogether.R;

public class MainActivity extends AppCompatActivity {
    long tripId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Bundle b = getIntent().getExtras();
        tripId = -1; // or other values
        if (b != null)
            tripId = b.getLong("tripId");
        
    }
}
