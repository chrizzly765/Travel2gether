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


import java.text.DecimalFormat;
import java.util.Currency;

import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.StaticTripData;
import de.traveltogether.model.Participant;
import de.traveltogether.model.Payer;

public class ParticipantSelectionListAdapter extends BaseAdapter {
    private Payer[] payer;
    private final LayoutInflater inflater;

    public ParticipantSelectionListAdapter(Context context, Payer[] _payer) {
        inflater = LayoutInflater.from(context);
        payer = _payer;
    }

    public void refresh(Payer[] _payer){
        payer = _payer;
    }

    @Override
    public int getCount() {
        return payer.length;
    }

    @Override
    public Object getItem(int position) {
        if(payer.length>0) {
            return payer[position];
        }
        else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        if(payer.length>0) {
            return payer[position].getId();
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
            //holder.amount=(EditText) convertView.findViewById(R.id.fragment_participant_selection_list_amount);
            convertView.setTag(holder);
        }
        else{
            holder = (ParticipantSelectionListViewHolder) convertView.getTag();
        }

        Context context = parent.getContext();
        Payer payer = (Payer) getItem(position);
        holder.name.setText(StaticTripData.getNameById(payer.getId()));
        holder.initial.setText(StaticTripData.getNameById(payer.getId()).substring(0,1));
        holder.icon.setBackgroundResource(StaticData.getIdForColor(StaticTripData.getColorById(payer.getId()))); // TODO: set color!
        return convertView;
    }


    public class ParticipantSelectionListViewHolder {
        public TextView name;
        public TextView initial;
        public ImageView icon;
        //public EditText amount;
    }
}
