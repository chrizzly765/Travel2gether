package de.traveltogether.tasks;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import de.traveltogether.R;
import de.traveltogether.model.Task;
import de.traveltogether.tasks.newtask.NewTaskActivity;

public class TaskListActivity extends AppCompatActivity implements View.OnClickListener {

    long tripId;
    ITaskListPresenter presenter;
    //private Task[] tasks;
    //TaskListFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        getSupportActionBar().setTitle("Meine Aufgaben");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.logo_ohne_schrift);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        Bundle b = getIntent().getExtras();
        tripId = -1;
        if(b != null) {
            tripId = b.getLong("tripId");
        }

        ImageButton newTaskBtn = (ImageButton) findViewById(R.id.activity_task_list_button_add);
        newTaskBtn.setOnClickListener(this);
        presenter = new TaskListPresenter(this);
        presenter.onGetTasks(tripId);
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.activity_task_list_button_add) {
            Intent intent = new Intent(this, NewTaskActivity.class);
            intent.putExtra("tripId", tripId);
            startActivity(intent);
        }
    }

    public void onViewTasks(Task[] _tasks){
        Log.d("TaskListActivity", "got tasks: "+_tasks.length);

        //Fragment in Activity einbetten
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TaskListFragment fragment = TaskListFragment.newInstance(_tasks);
        fragmentTransaction.add(R.id.fragment_task_list_container, fragment);
        fragmentTransaction.commit();
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
}
