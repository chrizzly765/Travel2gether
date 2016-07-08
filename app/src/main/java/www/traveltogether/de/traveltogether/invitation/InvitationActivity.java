package www.traveltogether.de.traveltogether.invitation;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import www.traveltogether.de.traveltogether.R;
import www.traveltogether.de.traveltogether.StaticData;
import www.traveltogether.de.traveltogether.model.Person;
import www.traveltogether.de.traveltogether.triplist.TripListActivity;

import static android.content.Intent.createChooser;

public class InvitationActivity extends AppCompatActivity implements View.OnClickListener{

    IInvitePresenter presenter;
    Person[] formerParticipants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);

        Button continueButton = (Button)findViewById(R.id.invitation_button_continue);
        continueButton.setOnClickListener(this);

        presenter = new InvitePresenter(this);
        presenter.onGetFormerParticipants();
        /*FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        InvitationFragment fragment = InvitationFragment.newInstance();
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
        */

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.invitation_button_continue) {
            Intent tripList = new Intent(this, TripListActivity.class);
            startActivity(tripList);
            finish();
        }

    }

    public void onViewError(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(getString(R.string.error));
        builder.setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onViewFormerParticipants(Person[] persons){
        formerParticipants = persons;
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        InvitationFragment fragment = InvitationFragment.newInstance(formerParticipants);
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}
