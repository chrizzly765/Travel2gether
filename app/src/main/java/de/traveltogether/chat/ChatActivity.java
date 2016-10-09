package de.traveltogether.chat;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import de.traveltogether.R;
import de.traveltogether.comments.CommentFragment;
import de.traveltogether.mainmenu.MainActivity;

public class ChatActivity extends AppCompatActivity {
    long tripId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Bundle b = getIntent().getExtras();
        tripId = -1;
        if (b != null) {
            tripId = b.getLong("tripId");
        }
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ChatFragment fragment = ChatFragment.newInstance(tripId);
        fragmentTransaction.add(R.id.activity_chat_comment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("tripId", tripId);
        startActivity(intent);
        finish();
    }
}
