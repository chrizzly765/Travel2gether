package de.traveltogether.info;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.util.List;


public class InfoParticipantAdapter extends BaseAdapter {
    Participant[] participants;
    private final LayoutInflater inflater;

    public InfoParticipantAdapter(Context context, Participant[] _participants) {
        inflater = LayoutInflater.from(context);
        participants=_participants;
    }

    public void refresh(Participant[] _participants){
        participants = _participants;
    }

    @Override
    public int getCount() {
        return participants.length;
    }

    @Override
    public Object getItem(int position) {
        if(participants.length>0) {
            return participants[position];
        }
        else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        if(participants.length>0) {
            return participants[position].getPersonId();
        }
        else{
            return -1;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ParticipantViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_info_list_item, parent, false);
            holder = new ParticipantViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.fragment_info_list_item_name);
            //holder.description= (TextView)convertView.findViewById(R.id.fragment_trip_list_item_description);
            holder.icon = (FrameLayout) convertView.findViewById(R.id.fragment_info_list_item_icon);
            convertView.setTag(holder);
        } else {
            holder = (ParticipantViewHolder) convertView.getTag();
        }

        Context context = parent.getContext();
        Participant participant = (Participant) getItem(position);
        holder.name.setText(participant.getUserName());

        TextView initial = (TextView) holder.icon.findViewById(R.id.fragment_info_list_item_initial);
        initial.setText(participant.getUserName().substring(0, 1));

        ImageView dot = (ImageView) holder.icon.findViewById(R.id.fragment_info_list_item_icon_dot);
        dot.setBackgroundResource(StaticData.getIdForColor(participant.getColor()));
        return convertView;

    }

    static class ParticipantViewHolder {
        TextView name;
        FrameLayout icon;
    }
}
