package de.traveltogether.tasks;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.model.Participant;
import de.traveltogether.model.Task;

public class TaskAdapter extends BaseAdapter {

    private Task[] taskList;
    private final LayoutInflater inflater;

    public TaskAdapter(Context context, Task[] _tasks) {
        inflater = LayoutInflater.from(context);
        taskList = _tasks;
    }

    public void refresh(Task[] tasks){
        taskList = tasks;
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
            return taskList[position].getId();
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
            holder.icon = (FrameLayout)convertView.findViewById(R.id.fragment_task_list_item_icon);
            holder.watch = (ImageView)convertView.findViewById(R.id.fragment_task_list_item_icon_watch);
            holder.toDoTillDate=(TextView)convertView.findViewById(R.id.fragment_task_list_item_toDoTillDate);
            convertView.setTag(holder);
        }
        else{
            holder = (TaskViewHolder)convertView.getTag();
        }

        Context context = parent.getContext();
        Task task = (Task)getItem(position);
        holder.title.setText(task.getTitle());
        //holder.description.setText(task.getDescription());
        // TODO: check date

        holder.toDoTillDate.setTextColor(Color.RED);
        holder.watch.setBackgroundResource(R.drawable.ic_watch_red);
        holder.toDoTillDate.setText(task.getDueDate());
        holder.icon.findViewById(R.id.fragment_task_list_item_icon_circle).setBackgroundResource(
                StaticData.getIdForColor(StaticData.getColorById(task.getPersonId()))
        );

        ((TextView)holder.icon.findViewById(R.id.fragment_task_list_item_icon_initial)).setText(
                StaticData.getNameById(task.getPersonId()).substring(0,1)
        );

        return convertView;
    }

    static class TaskViewHolder {
        TextView title, toDoTillDate;
        FrameLayout icon;
        ImageView watch;

    }
}
