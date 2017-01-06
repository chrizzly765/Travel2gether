package de.traveltogether.invitation;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import de.traveltogether.R;
import de.traveltogether.model.Person;

public class InvitationActivity extends AppCompatActivity{

    private IInvitePresenter presenter;
    private Person[] formerParticipants;
    private long tripId;
    private String formerActivity;
    private InvitationFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();

        getSupportActionBar().setTitle("Mitreisende hinzufügen");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setLogo(R.mipmap.logo_ohne_schrift);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        tripId = -1; // or other values
        if (b != null) {
            tripId = b.getLong("tripId");
            formerActivity = b.getString("formerActivity", "newTrip");
        }
        setContentView(R.layout.activity_invitation);
        presenter = new InvitePresenter(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
        presenter.onGetFormerParticipants(tripId);
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


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_save:
                finish();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
        if(formerParticipants== null){
            formerParticipants= new Person[0];
        }
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment = InvitationFragment.newInstance(formerParticipants, presenter, tripId);
        fragmentTransaction.add(R.id.fragment_invitation_container, fragment);
        fragmentTransaction.commit();
    }

    public void onInviteSuccess(){
        Context context = getApplicationContext();
        CharSequence text = getString(R.string.invite_success);
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
