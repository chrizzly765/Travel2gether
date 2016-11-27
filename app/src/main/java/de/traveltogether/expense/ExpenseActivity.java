package de.traveltogether.expense;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.StaticTripData;
import de.traveltogether.expense.newexpense.NewExpenseActivity;
import de.traveltogether.mainmenu.MainActivity;
import de.traveltogether.model.Expense;
import de.traveltogether.model.Participant;
import de.traveltogether.triplist.newtrip.NewTripActivity;

public class ExpenseActivity extends AppCompatActivity implements View.OnClickListener{
    long tripId;
    IExpensePresenter presenter;
    ProgressDialog progressDialog;
    ExpenseListFragment fragment;
    ExpenseParticipantFragment participantsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Ausgaben");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setLogo(R.mipmap.logo_ohne_schrift);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setContentView(R.layout.activity_expense);
        ImageButton add= (ImageButton)findViewById(R.id.activity_expense_button_add);
        add.setOnClickListener(this);

        tripId = getIntent().getLongExtra("tripId", -1);

        if(tripId!=-1){
            StaticTripData.setCurrentTripId(tripId);
        }
        presenter = new ExpensePresenter(this);

    }

    @Override
    protected void onStart(){
        super.onStart();
        progressDialog = ProgressDialog.show(this, "",
                "Bitte warten...", true);
        presenter.onGetParticipantList(tripId);
        presenter.onGetExpenseList(tripId);
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.activity_expense_button_add){
            Intent intent = new Intent(this, NewExpenseActivity.class);
            intent.putExtra("tripId", tripId);
            startActivity(intent);
        }
    }

    public void onViewError(String message){
        progressDialog.cancel();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(getString(R.string.error));
        builder.setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onViewParticipants(Participant[] participants){
        StaticTripData.setParticipants(participants);
        Participant[] activeParts =StaticTripData.getActiveParticipants();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        participantsFragment = ExpenseParticipantFragment.newInstance(activeParts);
        fragmentTransaction.add(R.id.activity_expense_participants_container, participantsFragment);
        fragmentTransaction.commit();

        //RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_expense_participants_container);
        //ViewGroup.LayoutParams params = layout.getLayoutParams();
        //params.height = participants.length * 50;
    }

    public void onViewExpenses(Expense[] expenses){

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment = ExpenseListFragment.newInstance(expenses);
        fragmentTransaction.add(R.id.activity_expense_list_container, fragment);
        fragmentTransaction.commit();

        if(progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

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
