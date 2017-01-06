package de.traveltogether.chat;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import de.traveltogether.R;
import de.traveltogether.StaticData;

public class ChatActivity extends AppCompatActivity {
    private long tripId;
    private ChatFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Chat");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setLogo(R.mipmap.logo_ohne_schrift);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        setContentView(R.layout.activity_chat);
        Bundle b = getIntent().getExtras();
        tripId = -1;
        if (b != null) {
            tripId = b.getLong("tripId");
        }

    }

    @Override
    protected void onStart(){
        super.onStart();
        StaticData.currentActivity = this;

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment = ChatFragment.newInstance(tripId);
        fragmentTransaction.add(R.id.activity_chat_comment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(fragment!=null)
            fragmentTransaction.detach(fragment);
        fragmentTransaction.commit();
        super.onSaveInstanceState(outState);
    }

    public void update(){
        fragment.onRefresh();
    }

    protected void onStop(){
        super.onStop();
        StaticData.currentActivity = null;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                super.onOptionsItemSelected(item);
        }
        return  true;
    }
}
