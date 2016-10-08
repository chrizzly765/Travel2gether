package de.traveltogether.activity.detailactivity;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.model.Expense;
import de.traveltogether.model.Payer;
import de.traveltogether.model.Activity;

import java.util.List;

public class DetailActivityAdapter extends BaseAdapter {
    Activity[] activities;
    LayoutInflater inflater;

    public DetailActivityAdapter(Context context, Activity[] _activity) {
        inflater = LayoutInflater.from(context);
        activities = _activity;
    }

    @Override
    public int getCount() {
        return activities.length;
    }

    @Override
    public Object getItem(int position) {
        return activities[position];
    }

    @Override
    public long getItemId(int position) {
        return activities[position].getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DetailActivityViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_expense_detail_item, parent, false);
            holder = new DetailActivityViewHolder();
            holder.name = (TextView)convertView.findViewById(R.id.fragment_expense_detail_item_name);
            //holder.description= (TextView)convertView.findViewById(R.id.fragment_trip_list_item_description);
            holder.amount=(TextView)convertView.findViewById(R.id.fragment_expense_detail_item_amount);
            convertView.setTag(holder);
        }
        else{
            holder = (DetailActivityViewHolder) convertView.getTag();
        }

        Context context = parent.getContext();
        Payer payer = (Payer) getItem(position);
        Log.d("payer id", String.valueOf(payer.getId()) + " " + StaticData.getNameById(payer.getId()));
        holder.name.setText(StaticData.getNameById(payer.getId()));
        String amount = "";
        amount= "- " + String.valueOf(payer.getAmount()) + "€";//get right currency?
        holder.amount.setTextColor(Color.RED);


        holder.amount.setText(amount);
        return convertView;
    }

    public class DetailActivityViewHolder {
        TextView name, amount;
    }
}
