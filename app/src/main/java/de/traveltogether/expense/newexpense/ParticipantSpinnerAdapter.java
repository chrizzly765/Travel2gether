package de.traveltogether.expense.newexpense;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import de.traveltogether.model.Participant;


class ParticipantSpinnerAdapter extends ArrayAdapter {
    private Participant[] participants;

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
