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
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import de.traveltogether.R;
import de.traveltogether.StaticTripData;
import de.traveltogether.mainmenu.MainActivity;
import de.traveltogether.model.Task;
import de.traveltogether.tasks.newtask.NewTaskActivity;

public class TaskListActivity extends AppCompatActivity implements View.OnClickListener {

    long tripId;
    ITaskListPresenter presenter;
    ProgressDialog progressDialog;
    TaskListFragment fragmentOpen;
    TaskListFragment fragmentProgress;
    TaskListFragment fragmentDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        getSupportActionBar().setTitle(R.string.tasks);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.logo_ohne_schrift);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

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

        Log.d("TaskListActivity", "got tasks: "+_tasks.length);

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
        }
        else {
            findViewById(R.id.activity_task_list_open_empty).setVisibility(View.VISIBLE);
            //onEmpty(R.id.activity_task_list_empty_txt_open);
        }

        if(taskListProgress.size() > 0) {
            /*RelativeLayout rel = (RelativeLayout)findViewById(R.id.activity_task_list_divider_progress);
            RelativeLayout.LayoutParams lp =(RelativeLayout.LayoutParams)rel.getLayoutParams();
            lp.addRule(RelativeLayout.BELOW, R.id.activity_task_list_container_open);
            rel.setLayoutParams(lp);*/

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            arr = new Task[taskListProgress.size()];
            taskListProgress.toArray(arr);
            fragmentProgress = TaskListFragment.newInstance(arr);
            fragmentTransaction.add(R.id.activity_task_list_container_progress, fragmentProgress);
            fragmentTransaction.commit();
        }
        else {
            findViewById(R.id.activity_task_list_progress_empty).setVisibility(View.VISIBLE);
            //onEmpty(R.id.activity_task_list_empty_txt_progress);
        }

        if(taskListDone.size() > 0) {
            /*RelativeLayout rel = (RelativeLayout)findViewById(R.id.activity_task_list_divider_done);
            RelativeLayout.LayoutParams lp =(RelativeLayout.LayoutParams)rel.getLayoutParams();
            lp.addRule(RelativeLayout.BELOW, R.id.activity_task_list_container_progress);
            rel.setLayoutParams(lp);*/

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            arr = new Task[taskListDone.size()];
            taskListDone.toArray(arr);
            fragmentDone = TaskListFragment.newInstance(arr);
            fragmentTransaction.add(R.id.activity_task_list_container_done, fragmentDone);
            fragmentTransaction.commit();
        }
        else {
            findViewById(R.id.activity_task_list_done_empty).setVisibility(View.VISIBLE);
            //onEmpty(R.id.activity_task_list_empty_txt_done);
        }
        progressDialog.cancel();
    }

    /*public void onEmpty(int id) {
        TextView txt = (TextView)findViewById(id);
        txt.setVisibility(View.VISIBLE);
        txt.setText(R.string.no_entries);
        txt.setHeight(20);
    }*/

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
}
