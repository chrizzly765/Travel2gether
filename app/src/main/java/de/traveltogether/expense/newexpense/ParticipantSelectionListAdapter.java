package de.traveltogether.expense.newexpense;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;


import de.traveltogether.R;
import de.traveltogether.model.Participant;

public class ParticipantSelectionListAdapter extends BaseAdapter {
    private Participant[] participants;
    private final LayoutInflater inflater;

    public ParticipantSelectionListAdapter(Context context, Participant[] _participants) {
        inflater = LayoutInflater.from(context);
        participants = _participants;
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
        ParticipantSelectionListViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_participant_selection_list_item, parent, false);
            holder = new ParticipantSelectionListViewHolder();
            holder.name = (TextView)convertView.findViewById(R.id.fragment_participant_selection_list_item_name);
            //holder.description= (TextView)convertView.findViewById(R.id.fragment_trip_list_item_description);
            holder.initial=(TextView)convertView.findViewById(R.id.fragment_participant_selection_list_item_initial);
            holder.icon=(ImageView)convertView.findViewById(R.id.fragment_participant_selection_list_item_icon);
            holder.amount=(EditText) convertView.findViewById(R.id.fragment_participant_selection_list_amount);
            convertView.setTag(holder);
        }
        else{
            holder = (ParticipantSelectionListViewHolder) convertView.getTag();
        }

        Context context = parent.getContext();
        Participant participant = (Participant) getItem(position);
        holder.name.setText(participant.getUserName());
        holder.initial.setText(participant.getUserName().substring(0,1));
        //holder.icon.setImageDrawable(trip.getStartDate()); // TODO: set color!
        //holder.amount.setText(); //TODO: set amount
        return convertView;
    }


    public class ParticipantSelectionListViewHolder {
        public TextView name;
        public TextView initial;
        public ImageView icon;
        public EditText amount;
    }
}
