package de.traveltogether.expense;

import android.content.Context;
import android.graphics.Color;
import android.provider.Telephony;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.StaticTripData;
import de.traveltogether.model.Expense;
import de.traveltogether.model.Participant;

import java.text.DecimalFormat;
import java.util.List;

public class ExpenseParticipantAdapter extends BaseAdapter {
    Participant[] participants;
    LayoutInflater inflater;

    public ExpenseParticipantAdapter(Context context, Participant[] _participants) {
        inflater = LayoutInflater.from(context);
        participants = _participants;
    }

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
        ParticipantViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_expense_participant_list_item, parent, false);
            holder = new ParticipantViewHolder();
            holder.name = (TextView)convertView.findViewById(R.id.fragment_expense_participant_list_item_name);
            holder.amount=(TextView)convertView.findViewById(R.id.fragment_expense_participant_list_item_amount);
            holder.dot = (ImageView)convertView.findViewById(R.id.fragment_expense_participant_color_dot);
            convertView.setTag(holder);
        }
        else{
            holder = (ParticipantViewHolder) convertView.getTag();
        }

        Context context = parent.getContext();
        Participant participant = (Participant) getItem(position);
        holder.name.setText(participant.getUserName());
        String amount ="";
        DecimalFormat df = new DecimalFormat("#0.00");
        if(participant.getAccountBalance()>=0){
            amount = "+" + String.valueOf(df.format(participant.getAccountBalance())) + "€";//Währung austauschbar?!
            holder.amount.setTextColor(Color.BLACK);
        }
        else{
            amount = String.valueOf(df.format(participant.getAccountBalance())) + "€";
            holder.amount.setTextColor(Color.RED);
        }
        holder.amount.setText(amount);
        holder.dot.setBackgroundResource(StaticData.getIdForColor(StaticTripData.getColorById(participant.getPersonId())));

        return convertView;
    }

    public class ParticipantViewHolder {
        TextView name, amount;
        ImageView dot;
    }
}
