package de.traveltogether.chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import de.traveltogether.R;
import de.traveltogether.comments.CommentFragment;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }
}
