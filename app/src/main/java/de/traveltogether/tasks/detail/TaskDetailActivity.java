package de.traveltogether.tasks.detail;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.StaticTripData;
import de.traveltogether.date.Date;
import de.traveltogether.dialog.DeleteActivity;
import de.traveltogether.mainmenu.MainActivity;
import de.traveltogether.model.Task;
import de.traveltogether.tasks.TaskListActivity;
import de.traveltogether.tasks.newtask.NewTaskActivity;

public class TaskDetailActivity extends DeleteActivity {

    private TextView title;
    private TextView description;
    private TextView status;
    private TextView toDoTillDate;
    private TextView name;
    private TextView initial;
    private ImageView icon;
    private ImageView watch;

    private Task task;
    private long featureId;
    private int colorStatus;
    private int colorDeadline;
    private int colorIcon;
    private long tripId;

    private ITaskDetailPresenter presenter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        getSupportActionBar().setTitle("Aufgabe");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.logo_ohne_schrift);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        presenter = new TaskDetailPresenter(this);
        featureId = getIntent().getLongExtra("featureId", -1);

        tripId=getIntent().getLongExtra("tripId", -1);
        if(tripId!=-1){
            StaticTripData.setCurrentTripId(tripId);
        }


        title = (TextView) findViewById(R.id.activity_task_detail_title);
        description = (TextView) findViewById(R.id.activity_task_detail_description);
        status = (TextView) findViewById(R.id.activity_task_detail_status);
        toDoTillDate = (TextView) findViewById(R.id.activity_task_detail_item_toDoTillDate);
        name = (TextView) findViewById(R.id.activity_task_detail_list_item_name);
        initial = (TextView) findViewById(R.id.activity_task_detail_list_item_initial);
        icon = (ImageView) findViewById(R.id.activity_task_detail_list_item_icon);
        watch = (ImageView)findViewById(R.id.activity_task_detail_item_icon_watch);

    }

    @Override
    protected void onStart(){
        super.onStart();
        progressDialog = ProgressDialog.show(this, "", "Aufgabe wird geladen...", true);
        if(featureId!=-1){
            presenter.onGetDetailsForTask(featureId);
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
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onViewDetails(Task _task){
        task = _task;
        tripId = task.getTripId();
        getSupportActionBar().setTitle(task.getTitle());
        title.setText(task.getTitle());
        description.setText(task.getDescription());

        switch (task.getState()) {
            case 1: colorStatus = Color.RED; break;
            case 2: colorStatus = getResources().getColor(R.color.colorffe154); break;
            case 3: colorStatus = getResources().getColor(R.color.color9bd641); break;
        }

        status.setText(getResources().getStringArray(R.array.status)[task.getState()-1]);
        status.setTextColor(colorStatus);

        Date date = new Date(task.getDueDate());

        if(date.compareDateWithCurrent()) {
            colorDeadline = Color.BLACK;
            colorIcon = R.drawable.ic_watch_black;
        }
        else {
            colorDeadline = Color.RED;
            colorIcon = R.drawable.ic_watch_red;
        }

        watch.setImageResource(colorIcon);
        toDoTillDate.setText(task.getDueDate());
        toDoTillDate.setTextColor(colorDeadline);
        if(task.getPersonId() != 0) {
            name.setText(StaticTripData.getNameById(task.getPersonId()));
            initial.setText(name.getText().toString().substring(0, 1));
            icon.setBackgroundResource(StaticData.getIdForColor(StaticTripData.getColorById(task.getPersonId())));
        }
        else {
            icon.setVisibility(View.INVISIBLE);
            name.setText(""); // empty field because of default value "Name"
        }

        progressDialog.cancel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionsmenu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:

                createDeleteDialog();
                break;
            case R.id.edit:
                Intent intent = new Intent(this, NewTaskActivity.class);
                Bundle b = new Bundle();
                b.putLong("featureId", task.getId());
                b.putLong("tripId", tripId);
                intent.putExtras(b);
                startActivity(intent);
                break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }
        return  true;
    }

    public void onSuccessDelete(){
        tripId = task.getTripId();
        Toast.makeText(getApplicationContext(), R.string.task_deleted, Toast.LENGTH_SHORT).show();
        finish();
    }


    public void onCloseActivity(){
        progressDialog.cancel();
        Context context = getApplicationContext();
        CharSequence text = "Diese Aufgabe wurde gelöscht.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        finish();
    }

    public void delete(){
        if(task.getAuthor() == StaticData.getUserId()){
            presenter.onDeleteTask(task);
        }
        else {
            Toast.makeText(getApplicationContext(), R.string.task_delete_warning, Toast.LENGTH_SHORT).show();
        }
    }

}
