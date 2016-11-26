package de.traveltogether.triplist;

import android.content.Context;
import android.provider.Telephony;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.traveltogether.R;
import de.traveltogether.StaticData;
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
            holder.icon1 = (FrameLayout)convertView.findViewById(R.id.fragment_trip_list_item_icon);
            holder.icon2 = (FrameLayout)convertView.findViewById(R.id.fragment_trip_list_item_icon_2);
            holder.icon3 = (FrameLayout)convertView.findViewById(R.id.fragment_trip_list_item_icon_3);
            holder.icon4 = (FrameLayout)convertView.findViewById(R.id.fragment_trip_list_item_icon_4);
            holder.icon5 = (FrameLayout)convertView.findViewById(R.id.fragment_trip_list_item_icon_5);
            holder.icon6 = (FrameLayout)convertView.findViewById(R.id.fragment_trip_list_item_icon_6);
            holder.icon7 = (FrameLayout)convertView.findViewById(R.id.fragment_trip_list_item_icon_7);
            holder.iconLast = (FrameLayout)convertView.findViewById(R.id.fragment_trip_list_item_icon_last);
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

        holder.icon1.setVisibility(View.INVISIBLE);
        holder.icon2.setVisibility(View.INVISIBLE);
        holder.icon3.setVisibility(View.INVISIBLE);
        holder.icon4.setVisibility(View.INVISIBLE);
        holder.icon5.setVisibility(View.INVISIBLE);
        holder.icon6.setVisibility(View.INVISIBLE);
        holder.icon7.setVisibility(View.INVISIBLE);
        holder.iconLast.setVisibility(View.INVISIBLE);

        ParticipantShort[] participants = trip.getParticipants();
        if(participants!=null && participants.length>0) {
            for(int i = 0; i<participants.length;i++) {
                if(i<9) {
                    ImageView bg = (ImageView) holder.icon1.findViewById(R.id.fragment_trip_list_item_icon_circle);
                    TextView initial = (TextView) holder.icon1.findViewById(R.id.fragment_trip_list_item_icon_initial);
                    if (i == 0) {
                        holder.icon1.setVisibility(View.VISIBLE);
                    }
                    else if (i == 1) {
                        holder.icon2.setVisibility(View.VISIBLE);
                        initial = (TextView) holder.icon2.findViewById(R.id.fragment_trip_list_item_icon_initial_2);
                        bg = (ImageView) holder.icon2.findViewById(R.id.fragment_trip_list_item_icon_circle_2);
                    }
                    else if (i == 2) {
                        holder.icon3.setVisibility(View.VISIBLE);
                        initial = (TextView) holder.icon3.findViewById(R.id.fragment_trip_list_item_icon_initial_3);
                        bg = (ImageView) holder.icon3.findViewById(R.id.fragment_trip_list_item_icon_circle_3);
                    }
                    else if (i == 3) {
                        holder.icon4.setVisibility(View.VISIBLE);
                        initial = (TextView) holder.icon4.findViewById(R.id.fragment_trip_list_item_icon_initial_4);
                        bg = (ImageView) holder.icon4.findViewById(R.id.fragment_trip_list_item_icon_circle_4);
                    }
                    else if (i == 4) {
                        holder.icon5.setVisibility(View.VISIBLE);
                        initial = (TextView) holder.icon5.findViewById(R.id.fragment_trip_list_item_icon_initial_5);
                        bg = (ImageView) holder.icon5.findViewById(R.id.fragment_trip_list_item_icon_circle_5);
                    }
                    else if (i == 5) {
                        holder.icon6.setVisibility(View.VISIBLE);
                        initial = (TextView) holder.icon6.findViewById(R.id.fragment_trip_list_item_icon_initial_6);
                        bg = (ImageView) holder.icon6.findViewById(R.id.fragment_trip_list_item_icon_circle_6);
                    }
                    else if (i == 6) {
                        holder.icon7.setVisibility(View.VISIBLE);
                        initial = (TextView) holder.icon7.findViewById(R.id.fragment_trip_list_item_icon_initial_7);
                        bg = (ImageView) holder.icon7.findViewById(R.id.fragment_trip_list_item_icon_circle_7);
                    }
                    initial.setText(participants[i].name.substring(0, 1));
                    bg.setBackgroundResource(StaticData.getIdForColor(participants[i].color));
                }
                else{
                    holder.iconLast.setVisibility(View.VISIBLE);
                }

            }
        }
        else{
            Log.e("TripAdapter", "Participants in trip is null");
        }
        return convertView;
    }

    static class TripViewHolder {
        TextView title, destination, date;
        FrameLayout icon1, icon2, icon3, icon4, icon5, icon6, icon7, iconLast;
    }
}
