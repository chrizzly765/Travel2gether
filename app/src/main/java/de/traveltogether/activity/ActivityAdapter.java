package de.traveltogether.activity;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import android.graphics.BitmapFactory;
import de.traveltogether.R;
import de.traveltogether.date.DateFormat;
import de.traveltogether.model.Activity;
import de.traveltogether.model.Trip;
import de.traveltogether.time.TimeFormat;


public class ActivityAdapter extends BaseAdapter  {
    private Activity[] formerActivities;
    private final LayoutInflater inflater;

    public ActivityAdapter(Context context, Activity[] activities) {
        inflater = LayoutInflater.from(context);
        formerActivities = activities;
    }

    public void refresh(Activity[] activities){
        formerActivities = activities;
    }

    @Override
    public int getCount() {
        return formerActivities.length;
    }

    @Override
    public Object getItem(int position) {
        if(formerActivities.length>0) {
            return formerActivities[position];
        }
        else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        if(formerActivities.length>0) {
            return formerActivities[position].getId();
        }
        else{
            return -1;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TripViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_activity_list_item, parent, false);
            holder = new TripViewHolder();
            holder.title = (TextView)convertView.findViewById(R.id.fragment_activity_list_item_title);
            holder.icon = (ImageView)convertView.findViewById(R.id.fragment_activity_list_item_icon);
            holder.destination=(TextView)convertView.findViewById(R.id.fragment_activity_list_item_place);
            holder.startDate=(TextView)convertView.findViewById(R.id.fragment_activity_list_item_date);
            holder.time=(TextView)convertView.findViewById(R.id.fragment_activity_list_item_time);
            convertView.setTag(holder);
        }
        else{
            holder = (TripViewHolder)convertView.getTag();
        }

        Context context = parent.getContext();
        Activity activity = (Activity)getItem(position);
        holder.title.setText(activity.getTitle());
        holder.icon.setBackgroundResource(activity.getIcon());
        //holder.date.setText(activity.getDate());
        holder.destination.setText(activity.getDestination());
        holder.startDate.setText(DateFormat.getInstance().getDateInWords(activity.getDate()));
        //holder.time.setText(activity.getTime());
        holder.time.setText(TimeFormat.getInstance().getTimeWithoutSecondsWithWord(activity.getTime()));
        return convertView;
    }

    static class TripViewHolder {
        TextView title, startDate, time, destination; //destination, startDate, endDate; //+ description
        ImageView icon;

    }
}
