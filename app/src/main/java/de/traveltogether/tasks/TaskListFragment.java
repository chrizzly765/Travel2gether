package de.traveltogether.tasks;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import de.traveltogether.R;
import de.traveltogether.mainmenu.MainActivity;
import de.traveltogether.model.Task;
//import de.traveltogether.tasks.detail.TaskDetailActivity;

import java.util.List;

public class TaskListFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private TaskAdapter adapter;
    private Task[] tasks;
    View view;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TaskListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TaskListFragment newInstance(Task[] _tasks ) {
        TaskListFragment fragment = new TaskListFragment();
        fragment.tasks = _tasks;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_task_list, container, false);

        return view;
    }

    public void onStart(){
        super.onStart();
        if(tasks==null || tasks.length==0 ){
            //TODO: show new task listitem
        }
        else {
            adapter = new TaskAdapter(getActivity(), tasks);
            setListAdapter(adapter);
            getListView().setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*Intent detail = new Intent(getActivity(), TaskDetailActivity.class);
        Bundle b = new Bundle();
        b.putLong("featureId", tasks[position].getId()); //Your id
        detail.putExtras(b); //Put your id to your next Intent
        startActivity(detail);
        */
    }

}
