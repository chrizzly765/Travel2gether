package de.traveltogether.expense.newexpense;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import de.traveltogether.model.Participant;

/**
 * Created by Anna-Lena on 11.09.2016.
 */
public class ParticipantSpinnerAdapter extends ArrayAdapter {
    Participant[] participants;

    public ParticipantSpinnerAdapter(Context context, int resource, Participant[] _participants) {
        super(context, resource);
        participants = _participants;
    }

    /*public ParticipantSpinnerAdapter(Participant[] _participants){
        participants = _participants;
        super();
    }*/


    @Override
    public int getCount() {
        return participants.length;
    }

    @Override
    public Object getItem(int position) {
        return participants[position];
    }

    @Override
    public long getItemId(int position) {
        return participants[position].getPersonId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //TODO
        return null;
    }


}
