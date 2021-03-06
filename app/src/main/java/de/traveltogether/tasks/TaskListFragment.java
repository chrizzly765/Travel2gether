package de.traveltogether.tasks;

import android.app.ListFragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import de.traveltogether.R;
import de.traveltogether.model.Task;
import de.traveltogether.tasks.detail.TaskDetailActivity;

/**
 * Fragment that contains list with tasks
 */
public class TaskListFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private TaskAdapter adapter;
    private Task[] tasks;
    private View view;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TaskListFragment() {
    }

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
        if(!(tasks==null || tasks.length==0) ){
            adapter = new TaskAdapter(getActivity(), tasks);
            setListAdapter(adapter);
            getListView().setOnItemClickListener(this);

            Resources resources = getActivity().getResources();
            DisplayMetrics metrics = resources.getDisplayMetrics();
            int px = (int)(96 * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
            int unbounded = View.MeasureSpec.makeMeasureSpec(px, View.MeasureSpec.AT_MOST);
            ViewGroup vg = getListView();
            int totalHeight = 0;
            for (int i = 0; i < adapter.getCount(); i++) {
                View listItem = adapter.getView(i, null, vg);
                listItem.measure(unbounded, unbounded);
                int measuredHeight = listItem.getMeasuredHeight();
                if(measuredHeight < px){
                    measuredHeight = px;
                }
                totalHeight += measuredHeight;
            }
            try {
                ViewGroup.LayoutParams par = getListView().getLayoutParams();
                par.height = totalHeight;
                getListView().setLayoutParams(par);
                getListView().requestLayout();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent detail = new Intent(getActivity(), TaskDetailActivity.class);
        Bundle b = new Bundle();
        b.putLong("featureId", tasks[position].getId());
        detail.putExtras(b);
        startActivity(detail);
    }
}
