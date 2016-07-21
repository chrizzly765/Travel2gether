package de.traveltogether.triplist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import de.traveltogether.R;
import de.traveltogether.model.Trip;

/**
 * Created by Anna-Lena on 12.06.2016.
 */
public class TripAdapter extends BaseAdapter {
    private final Trip[] tripList;
    private final LayoutInflater inflater;

    public TripAdapter(Context context, Trip[] _trips) {
        inflater = LayoutInflater.from(context);
        tripList = _trips;
    }

    @Override
    public int getCount() {
        return tripList.length;
    }

    @Override
    public Object getItem(int position) {
        if(tripList.length>0) {
            return tripList[position];
        }
        else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        if(tripList.length>0) {
            return tripList[position].getTripId();
        }
        else{
            return -1;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TripViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_trip_list_item, parent, false);
            holder = new TripViewHolder();
            holder.title = (TextView)convertView.findViewById(R.id.fragment_trip_list_item_title);
            holder.description= (TextView)convertView.findViewById(R.id.fragment_trip_list_item_description);
            holder.destination=(TextView)convertView.findViewById(R.id.fragment_trip_list_item_destination);
            holder.startDate=(TextView)convertView.findViewById(R.id.fragment_trip_list_item_startdate);
            holder.endDate=(TextView)convertView.findViewById(R.id.fragment_trip_list_item_enddate);
            convertView.setTag(holder);
        }
        else{
            holder = (TripViewHolder)convertView.getTag();
        }

        Context context = parent.getContext();
        Trip trip = (Trip)getItem(position);
        holder.title.setText(trip.getTitle());
        holder.description.setText(trip.getDescription());
        holder.destination.setText(trip.getDestination());
        holder.startDate.setText(trip.getStartDate());
        holder.endDate.setText(trip.getEndDate());
        return convertView;
    }

    static class TripViewHolder {
        TextView title, description, destination, startDate, endDate;

    }
}
