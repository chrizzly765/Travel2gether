package de.traveltogether.triplist;

import android.content.Context;
import android.provider.Telephony;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.traveltogether.R;
import de.traveltogether.date.DateFormat;
import de.traveltogether.model.Participant;
import de.traveltogether.model.ParticipantShort;
import de.traveltogether.model.Trip;

/**
 * Created by Anna-Lena on 12.06.2016.
 */
public class TripAdapter extends BaseAdapter {
    private Trip[] tripList;
    private final LayoutInflater inflater;

    public TripAdapter(Context context, Trip[] _trips) {
        inflater = LayoutInflater.from(context);
        tripList = _trips;
    }

    public void refresh(Trip[] trips){
        tripList = trips;
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
            //holder.description= (TextView)convertView.findViewById(R.id.fragment_trip_list_item_description);
            holder.destination=(TextView)convertView.findViewById(R.id.fragment_trip_list_item_destination);
            holder.date=(TextView)convertView.findViewById(R.id.fragment_trip_list_item_date);
            holder.icon = (FrameLayout)convertView.findViewById(R.id.fragment_trip_list_item_icon);
            convertView.setTag(holder);
        }
        else{
            holder = (TripViewHolder)convertView.getTag();
        }

        Context context = parent.getContext();
        Trip trip = (Trip)getItem(position);
        holder.title.setText(trip.getTitle());
        holder.destination.setText(trip.getDestination());
        holder.date.setText(DateFormat.getInstance().getDateFromToInWords(trip.getStartDate(), trip.getEndDate()));

        ParticipantShort[] participants = trip.getParticipants();
        if(participants!=null && participants.length>1) {
            holder.icon.setVisibility(View.VISIBLE);
            TextView initial = (TextView) holder.icon.findViewById(R.id.fragment_trip_list_item_icon_initial);
            initial.setText(participants[1].name.substring(0,1));
            return convertView;
        }
        else{
            Log.e("TripAdapter", "Participants in trip is null");
        }
        return convertView;
    }

    static class TripViewHolder {
        TextView title, destination, date;
        FrameLayout icon;
    }
}
