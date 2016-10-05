package de.traveltogether.packinglist;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.content.Context;
import de.traveltogether.R;

import de.traveltogether.model.PackingObject;




import java.util.List;

/**
 * Created by Maria on 18.09.2016
 */
public class PackingAdapter extends BaseAdapter {

    private PackingObject[] packingobjectList;
    private final LayoutInflater inflater;

    public PackingAdapter(Context context, PackingObject[] _packingobjects) {
        inflater = LayoutInflater.from(context);
        packingobjectList = _packingobjects;
    }

    public void refresh(PackingObject[] packingobjects){packingobjectList = packingobjects;}

    @Override
    public int getCount() {
        return packingobjectList.length;
    }

    @Override
    public Object getItem(int position) {
        if(packingobjectList.length>0) {
            return packingobjectList[position];
        }
        else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        if(packingobjectList.length>0) {
            return packingobjectList[position].getId();
        }
        else{
            return -1;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PackingViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_packing_list_item, parent, false);
            holder = new PackingViewHolder();
            holder.title = (CheckBox) convertView.findViewById(R.id.fragment_packing_list_item_checkbox);
            holder.number = (TextView) convertView.findViewById(R.id.fragment_packing_list_item_current_number);
            holder.itemsPacked = (TextView) convertView.findViewById(R.id.fragment_packing_list_item_number_packed_items);
            //holder.description= (TextView)convertView.findViewById(R.id.fragment_trip_list_item_description);
            //holder.destination=(TextView)convertView.findViewById(R.id.fragment_trip_list_item_destination);
            //holder.startDate=(TextView)convertView.findViewById(R.id.fragment_trip_list_item_startdate);
            //holder.endDate=(TextView)convertView.findViewById(R.id.fragment_trip_list_item_enddate);
            convertView.setTag(holder);
        }
        else{
            holder = (PackingViewHolder)convertView.getTag();
        }
        Context context = parent.getContext();
        PackingObject packingobject = (PackingObject)getItem(position);
        holder.title.setText(packingobject.getTitle());
        holder.number.setText(String.valueOf(packingobject.getPackingItemsNumber()));
        holder.itemsPacked.setText(String.valueOf(packingobject.getItemsPacked()) + "/" + String.valueOf(packingobject.getPackingItemsNumber()));
        //holder.description.setText(packingobject.getDescription());
        //holder.destination.setText(trip.getDestination());
        //holder.startDate.setText(trip.getStartDate());
        //holder.endDate.setText(trip.getEndDate());
        return convertView;
    }

    static class PackingViewHolder{
        CheckBox title;
        TextView number, itemsPacked;
    }
}
