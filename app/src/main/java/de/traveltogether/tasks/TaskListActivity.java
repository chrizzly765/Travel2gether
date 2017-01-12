package de.traveltogether.tasks;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import de.traveltogether.R;
import de.traveltogether.StaticTripData;
import de.traveltogether.model.Task;
import de.traveltogether.tasks.newtask.NewTaskActivity;

/**
 * Activity for task list
 */
public class TaskListActivity extends AppCompatActivity implements View.OnClickListener {

    private long tripId;
    private ITaskListPresenter presenter;
    private ProgressDialog progressDialog;
    private TaskListFragment fragmentOpen;
    private TaskListFragment fragmentProgress;
    private TaskListFragment fragmentDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        getSupportActionBar().setTitle(R.string.tasks);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle b = getIntent().getExtras();
        tripId = -1;
        if(b != null) {
            tripId = b.getLong("tripId");
        }
        if(tripId!=-1){
            StaticTripData.setCurrentTripId(tripId);
        }

        ImageButton newTaskBtn = (ImageButton) findViewById(R.id.activity_task_list_button_add);
        newTaskBtn.setOnClickListener(this);
        presenter = new TaskListPresenter(this);

    }

    @Override
    protected void onStart(){
        super.onStart();
        presenter.onGetTasks(tripId);
        progressDialog = ProgressDialog.show(this, "", getResources().getString(R.string.please_wait), true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(fragmentOpen!=null)
            fragmentTransaction.detach(fragmentOpen);
        if(fragmentProgress!=null)
            fragmentTransaction.detach(fragmentProgress);
        if(fragmentDone!=null)
            fragmentTransaction.detach(fragmentDone);

        fragmentTransaction.commit();
        super.onSaveInstanceState(outState);
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
        //adding task to open, progress or done list after checking state of the task
        List<Task> taskListOpen = new ArrayList<Task>();
        List<Task> taskListProgress = new ArrayList<Task>();
        List<Task> taskListDone = new ArrayList<Task>();

        for (Task t: _tasks) {
           if(t.getState() == 1) {
               taskListOpen.add(t);
            }
            else if(t.getState() == 2) {
                taskListProgress.add(t);
            }
            else if(t.getState() == 3) {
               taskListDone.add(t);
           }
        }

        FragmentManager fragmentManager = getFragmentManager();
        Task[] arr;

        if(taskListOpen.size() > 0) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            arr = new Task[taskListOpen.size()];
            taskListOpen.toArray(arr);
            fragmentOpen = TaskListFragment.newInstance(arr);
            fragmentTransaction.add(R.id.activity_task_list_container_open, fragmentOpen);
            fragmentTransaction.commit();
            findViewById(R.id.activity_task_list_open_empty).setVisibility(View.INVISIBLE);

        }
        else {
            findViewById(R.id.activity_task_list_open_empty).setVisibility(View.VISIBLE);
        }

        if(taskListProgress.size() > 0) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            arr = new Task[taskListProgress.size()];
            taskListProgress.toArray(arr);
            fragmentProgress = TaskListFragment.newInstance(arr);
            fragmentTransaction.add(R.id.activity_task_list_container_progress, fragmentProgress);
            fragmentTransaction.commit();
            findViewById(R.id.activity_task_list_progress_empty).setVisibility(View.INVISIBLE);
        }
        else {
            findViewById(R.id.activity_task_list_progress_empty).setVisibility(View.VISIBLE);
        }

        if(taskListDone.size() > 0) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            arr = new Task[taskListDone.size()];
            taskListDone.toArray(arr);
            fragmentDone = TaskListFragment.newInstance(arr);
            fragmentTransaction.add(R.id.activity_task_list_container_done, fragmentDone);
            fragmentTransaction.commit();
            findViewById(R.id.activity_task_list_done_empty).setVisibility(View.INVISIBLE);
        }
        else {
            findViewById(R.id.activity_task_list_done_empty).setVisibility(View.VISIBLE);
        }
        progressDialog.cancel();
    }

    public void onViewError(String message) {
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
