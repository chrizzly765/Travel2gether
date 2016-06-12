package www.traveltogether.de.traveltogether.invitation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import www.traveltogether.de.traveltogether.R;

import static android.content.Intent.createChooser;

public class InvitationActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);

        Button continueButton = (Button)findViewById(R.id.invitation_button_continue);
        continueButton.setOnClickListener(this);

        //This code should be in fragment not here!
        ImageButton button = (ImageButton) findViewById(R.id.button_invite);
        TextView text = (TextView)findViewById(R.id.text_invite);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent invite = new Intent(Intent.ACTION_SEND);
                invite.putExtra(Intent.EXTRA_TEXT, getString(R.string.invitation_text));
                invite.setType("text/plain");
                startActivity(createChooser(invite, getString(R.string.title_invititation_choose)));
            }
        });
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent invite = new Intent(Intent.ACTION_SEND);
                invite.putExtra(Intent.EXTRA_TEXT, getString(R.string.invitation_text));
                invite.setType("text/plain");
                startActivity(createChooser(invite, getString(R.string.title_invititation_choose)));
            }
        });
        //
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.invitation_button_continue) {
            Intent mainmenu = new Intent(this, www.traveltogether.de.traveltogether.mainmenu.MainActivity.class);
            startActivity(mainmenu);
            finish();
        }

    }
}
