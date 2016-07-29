package de.traveltogether.mainmenu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.activity.ActivitiesActivity;
import de.traveltogether.chat.ChatActivity;
import de.traveltogether.expense.ExpenseActivity;
import de.traveltogether.info.InfoActivity;
import de.traveltogether.packinglist.PackingListActivity;
import de.traveltogether.tasks.TaskListActivity;
import de.traveltogether.triplist.TripListActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    long tripId;
    String title;
    String adminId;
    IMainMenuPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MainMenuPresenter(this);
        setContentView(R.layout.activity_main_menu);
        Bundle b = getIntent().getExtras();
        tripId = -1; // or other values
        if (b != null) {
            tripId = b.getLong("tripId");
            title = b.getString("title");
            adminId = b.getString("adminId");
                setActionBar(title);
        }

        ImageButton info = (ImageButton)findViewById(R.id.main_menu_info);
        ImageButton expences = (ImageButton)findViewById(R.id.main_menu_expences);
        ImageButton tasks = (ImageButton)findViewById(R.id.main_menu_tasks);
        ImageButton packing = (ImageButton)findViewById(R.id.main_menu_packing);
        ImageButton activities = (ImageButton)findViewById(R.id.main_menu_activities);
        ImageButton chat = (ImageButton)findViewById(R.id.main_menu_chat);
    }

    public void setActionBar(String heading) {
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle(heading);
        actionBar.show();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.logo_ohne_schrift);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionsmenu_trip, menu);

        if(StaticData.getUserId().equals(adminId)){
            menu.getItem(0).setEnabled(true);
            menu.getItem(1).setEnabled(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.delete_trip:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(getString(R.string.trip_delete_warning));
                builder.setTitle(getString(R.string.trip_delete));
                builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        presenter.onDeleteTrip(tripId);
                        Intent tripList = new Intent(getApplicationContext(), TripListActivity.class);
                        startActivity(tripList);
                        finish();

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            case R.id.change_admin:
                //TODO: get participants, choose one of them
                return true;
            case R.id.leave_trip:
                AlertDialog.Builder adBuilder = new AlertDialog.Builder(this);
                adBuilder.setMessage(getString(R.string.leave_trip_warning));
                adBuilder.setTitle(getString(R.string.leave_trip));
                adBuilder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                adBuilder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        presenter.onLeaveTrip(tripId,StaticData.getUserId());
                        Intent tripList = new Intent(getApplicationContext(), TripListActivity.class);
                        startActivity(tripList);
                        finish();

                    }
                });

                AlertDialog alertDialog = adBuilder.create();
                alertDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }
    }

    public void onViewError(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(getString(R.string.error));
        builder.setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
    }

    public void onSuccessDeletingTrip(){
        Context context = getApplicationContext();
        CharSequence text = getString(R.string.delete_trip_success);
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void onSuccessLeavingTrip() {
        Context context = getApplicationContext();
        CharSequence text = getString(R.string.leave_trip_success);
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.main_menu_info){
            Intent intent = new Intent(this, InfoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("tripId", tripId);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else if(v.getId()==R.id.main_menu_expences){
            Intent intent = new Intent(this, ExpenseActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("tripId", tripId);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else if(v.getId()==R.id.main_menu_activities){
            Intent intent = new Intent(this, ActivitiesActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("tripId", tripId);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else if(v.getId()==R.id.main_menu_chat){
            Intent intent = new Intent(this, ChatActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("tripId", tripId);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else if(v.getId()==R.id.main_menu_tasks){
            Intent intent = new Intent(this, TaskListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("tripId", tripId);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else if(v.getId()==R.id.main_menu_packing){
            Intent intent = new Intent(this, PackingListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("tripId", tripId);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
