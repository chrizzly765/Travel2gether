package de.traveltogether.notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import de.traveltogether.DataType;
import de.traveltogether.R;
import de.traveltogether.model.Notification;
import de.traveltogether.model.Trip;

/**
 * Created by Anna-Lena on 12.06.2016.
 */
public class NotificationAdapter extends BaseAdapter {
    private Notification[] notificationList;
    private final LayoutInflater inflater;

    public NotificationAdapter(Context context, Notification[] _notifications) {
        inflater = LayoutInflater.from(context);
        notificationList = _notifications;
    }

    public void refresh(Notification[] notifications){
         notificationList = notifications;
    }

    @Override
    public int getCount() {
        return notificationList.length;
    }

    @Override
    public Object getItem(int position) {
        if(notificationList.length>0) {
            return notificationList[position];
        }
        else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        if(notificationList.length>0) {
            return notificationList[position].getId();
        }
        else{
            return -1;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NotificationViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_notification_list_item, parent, false);
            holder = new NotificationViewHolder();
            holder.title = (TextView)convertView.findViewById(R.id.fragment_notification_list_item_title);
            holder.date = (TextView)convertView.findViewById(R.id.fragment_notification_list_item_date);
            holder.icon = (ImageView)convertView.findViewById(R.id.fragment_notification_list_item_icon);
            //holder.description= (TextView)convertView.findViewById(R.id.fragment_trip_list_item_description);
            convertView.setTag(holder);
        }
        else{
            holder = (NotificationViewHolder)convertView.getTag();
        }

        Context context = parent.getContext();
        Notification not = (Notification) getItem(position);
        holder.title.setText(not.getMessage());
        holder.date.setText(not.getReceiveDate());
        if( not.getType().equals(DataType.EXPENSE.toString())){
            holder.icon.setImageResource(R.drawable.ic_expense);
            holder.icon.setBackgroundResource(R.drawable.circle1ddbc8);
        }
        else if(not.getType().equals(DataType.CHAT.toString())){
            holder.icon.setImageResource(R.drawable.ic_check);
            holder.icon.setBackgroundResource(R.drawable.circle37a9e3);
        }
        else if (not.getType().equals(DataType.PACKINGOBJECT.toString())){
            holder.icon.setImageResource(R.drawable.ic_packing);
            holder.icon.setBackgroundResource(R.drawable.circle27b3e2);

        }
        else if (not.getType().equals(DataType.TASK.toString())){
            holder.icon.setImageResource(R.mipmap.ic_check);
            holder.icon.setBackgroundResource(R.drawable.circle03c7c6);

        }
        else if (not.getType().equals(DataType.TRIP.toString())){
            holder.icon.setImageResource(R.mipmap.ic_info);
            holder.icon.setBackgroundResource(R.drawable.circle00e6b6);

        }
        else if (not.getType().equals(DataType.ACTIVITY.toString())){
            holder.icon.setImageResource(R.drawable.ic_activity);
            holder.icon.setBackgroundResource(R.drawable.circle2cc0d3);

        }
        else if (not.getType().equals(DataType.INVITATION.toString())){
            holder.icon.setImageResource(R.drawable.ic_notification);
            holder.icon.setBackgroundResource(R.drawable.circle00e6b6);

        }

        if(not.getOpened()==true) {
            convertView.setBackgroundResource(R.drawable.rectangle);
        }
        else{
            convertView.setBackgroundResource(R.drawable.rectangle_light_grey);
        }
        return convertView;
    }

    static class NotificationViewHolder {
        TextView title, date;
        ImageView icon;
    }
}
