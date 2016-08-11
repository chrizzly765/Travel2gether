package de.traveltogether.tasks;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import de.traveltogether.R;
import de.traveltogether.model.Task;
import de.traveltogether.tasks.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TaskAdapter extends BaseAdapter {

    private Task[] taskList;
    private final LayoutInflater inflater;

    public TaskAdapter(Context context, Task[] _tasks) {
        inflater = LayoutInflater.from(context);
        taskList = _tasks;
    }

    public void refresh(Task[] trips){
        taskList = trips;
    }

    @Override
    public int getCount() {
        return taskList.length;
    }

    @Override
    public Object getItem(int position) {
        if(taskList.length>0) {
            return taskList[position];
        }
        else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        if(taskList.length>0) {
            return taskList[position].getTaskId();
        }
        else{
            return -1;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TaskViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_task_list_item, parent, false);
            holder = new TaskViewHolder();
            holder.title = (TextView)convertView.findViewById(R.id.fragment_task_list_item_title);
            //holder.description= (TextView)convertView.findViewById(R.id.fragment_trip_list_item_description);
            holder.destination=(TextView)convertView.findViewById(R.id.fragment_task_list_item_destination);
            holder.startDate=(TextView)convertView.findViewById(R.id.fragment_task_list_item_startdate);
            holder.endDate=(TextView)convertView.findViewById(R.id.fragment_task_list_item_enddate);
            convertView.setTag(holder);
        }
        else{
            holder = (TaskViewHolder)convertView.getTag();
        }

        Context context = parent.getContext();
        Task trip = (Task)getItem(position);
        holder.title.setText(trip.getTitle());
        //holder.description.setText(trip.getDescription());
        holder.destination.setText(trip.getDestination());
        holder.startDate.setText(trip.getStartDate());
        holder.endDate.setText(trip.getEndDate());
        return convertView;
    }

    static class TaskViewHolder {
        TextView title, destination, startDate, endDate; //+ description

    }
}
