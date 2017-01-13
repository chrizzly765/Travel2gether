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
 * Adapter for invitation suggestions.
 * Putting together view and data that was called from server.
 */
 class InvitationAdapter extends BaseAdapter {
    private final Person[] formerParticipants;
    private final LayoutInflater inflater;

     InvitationAdapter(Context context, Person[] persons) {
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
        ParticipantViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_invitation_list_item, parent, false);
            holder = new ParticipantViewHolder();
            holder.name = (TextView)convertView.findViewById(R.id.fragment_invitation_list_item_name);
            holder.grayIcon = (ImageView)convertView.findViewById(R.id.fragment_invitation_list_item_icon);
            holder.initial = (TextView) convertView.findViewById(R.id.fragment_invitation_list_item_initial);
            convertView.setTag(holder);
        }
        else{
            holder = (ParticipantViewHolder)convertView.getTag();
        }

        Person person = (Person)getItem(position);
        if(person.getUserName()!=null) {
            holder.name.setText(person.getUserName());
            holder.initial.setText(person.getUserName().substring(0, 1));
        }
        return convertView;
    }

    /**
     * ViewHolder for participant.
     * Holds all elements of one participant from the list that are transformed later.
     */
    private static class ParticipantViewHolder {
        ImageView grayIcon;
        TextView name, initial;
    }
}
