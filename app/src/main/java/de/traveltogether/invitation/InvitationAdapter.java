package de.traveltogether.invitation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import de.traveltogether.R;
import de.traveltogether.model.Person;

/**
 * Created by Anna-Lena on 12.06.2016.
 * Schnittstelle zwischen UI und Daten des Invitationfrag
 */
public class InvitationAdapter extends BaseAdapter {
    private final Person[] formerParticipants;
    private final LayoutInflater inflater;

    public InvitationAdapter(Context context, Person[] persons) {
        inflater = LayoutInflater.from(context);
        formerParticipants = persons;
    }

    @Override
    public int getCount() {
        return formerParticipants.length;
    }

    @Override
    public Object getItem(int position) {
        if(formerParticipants.length>0) {
            return formerParticipants[position];
        }
        else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        if(formerParticipants.length>0) {
            return formerParticipants[position].getPersonId();
        }
        else{
            return -1;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TripViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_invitation_list_item, parent, false);
            holder = new TripViewHolder();
            holder.name = (TextView)convertView.findViewById(R.id.fragment_invitation_list_name);
            holder.grayIcon = (ImageView)convertView.findViewById(R.id.fragment_invitation_list_icon);
            convertView.setTag(holder);
        }
        else{
            holder = (TripViewHolder)convertView.getTag();
        }

        Context context = parent.getContext();
        Person person = (Person)getItem(position);
        holder.name.setText(person.getUserName());
        return convertView;
    }

    static class TripViewHolder {
        ImageView grayIcon;
        TextView name;
    }
}
