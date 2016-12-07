package de.traveltogether.activity;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.graphics.BitmapFactory;

import org.apache.commons.lang3.StringEscapeUtils;

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

            holder.layout = (LinearLayout)convertView.findViewById(R.id.fragment_activity_list_item_background);
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

        if (position%2 == 0) {
            holder.layout.setBackgroundResource(R.mipmap.background_activity);
        }
        else {
            holder.layout.setBackgroundResource(R.mipmap.background_activity_2);
        }

        if (position == (getCount()-1)){
            holder.layout.setBackgroundResource(R.mipmap.background_activity_end);
        }
        if ( (position == (getCount()-1) && !(position%2 == 0) ) ){
            holder.layout.setBackgroundResource(R.mipmap.background_activity_end_2);
        }


        holder.title.setText(StringEscapeUtils.unescapeJava(activity.getTitle()));
        if(activity.getIcon()!= R.mipmap.ic_leer) {
            holder.icon.setBackgroundResource(activity.getIcon());
        }
        //holder.date.setText(activity.getDate());
        holder.destination.setText(StringEscapeUtils.unescapeJava(activity.getDestination()));
        holder.startDate.setText(DateFormat.getInstance().getDateInWords(activity.getDate()));
        //holder.time.setText(activity.getTime());
        holder.time.setText(TimeFormat.getInstance().getTimeWithoutSecondsWithWord(activity.getTime()));
        return convertView;
    }

    static class TripViewHolder {
        TextView title, startDate, time, destination; //destination, startDate, endDate; //+ description
        ImageView icon;
        LinearLayout layout;

    }
}
