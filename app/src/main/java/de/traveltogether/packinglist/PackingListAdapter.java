package de.traveltogether.packinglist;

import android.content.res.Resources;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import de.traveltogether.R;

import de.traveltogether.StaticData;
import de.traveltogether.StaticTripData;
import de.traveltogether.model.PackingObject;




import java.util.List;

/**
 * Created by Maria on 18.09.2016
 */
public class PackingListAdapter extends BaseAdapter {

    private PackingObject[] packingobjectList;
    private final LayoutInflater inflater;

    public PackingListAdapter(Context context, PackingObject[] _packingobjects) {
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
            holder.title = (TextView) convertView.findViewById(R.id.fragment_packing_list_item_title);
            holder.check = (ImageView)convertView.findViewById(R.id.fragment_packing_list_item_checkbox);
            holder.itemsPacked = (TextView) convertView.findViewById(R.id.fragment_packing_list_item_number_packed_items);
            holder.icon1 = (FrameLayout) convertView.findViewById(R.id.fragment_packing_list_item_icon_1);
            holder.icon2 = (FrameLayout) convertView.findViewById(R.id.fragment_packing_list_item_icon_2);
            holder.icon3 = (FrameLayout) convertView.findViewById(R.id.fragment_packing_list_item_icon_3);
            holder.icon4 = (FrameLayout) convertView.findViewById(R.id.fragment_packing_list_item_icon_4);

            convertView.setTag(holder);
        }
        else{
            holder = (PackingViewHolder)convertView.getTag();
        }
        PackingObject packingobject = (PackingObject)getItem(position);
        holder.title.setText(packingobject.getTitle());
        if((packingobject.getItemsPacked() == packingobject.getPackingItemsNumber()) && (packingobject.getItemsPacked()!=0)){
            holder.check.setImageResource(R.drawable.checkbox_filled);
        }
        holder.itemsPacked.setText(String.valueOf(packingobject.getItemsPacked()) + "/" + String.valueOf(packingobject.getPackingItemsNumber()));
        if(packingobject.getPersonsAssigned().length>0) {
            for (int i = 0; i < packingobject.getPersonsAssigned().length; i++) {
                if (i==0){
                    holder.icon1.setVisibility(View.VISIBLE);
                    if(packingobject.getPersonsAssigned().length>4){
                        holder.icon1.findViewById(R.id.fragment_packing_list_item_icon_dots_1).setVisibility(View.VISIBLE);
                    }else {
                        ((TextView)holder.icon1.findViewById(R.id.fragment_packing_list_item_icon_initial_1))
                                .setText(StaticTripData.getNameById(packingobject.getPersonsAssigned()[i]).substring(0,1));
                        ((ImageView) holder.icon1.findViewById(R.id.fragment_packing_list_item_icon_circle_1))
                                .setBackgroundResource(
                                        StaticData.getIdForColor(StaticTripData.getColorById(packingobject.getPersonsAssigned()[i])
                                        ));
                    }
                }else if(i==1){
                    holder.icon2.setVisibility(View.VISIBLE);
                    ((TextView)holder.icon2.findViewById(R.id.fragment_packing_list_item_icon_initial_2))
                            .setText(StaticTripData.getNameById(packingobject.getPersonsAssigned()[i]).substring(0,1));
                    ((ImageView) holder.icon2.findViewById(R.id.fragment_packing_list_item_icon_circle_2))
                            .setBackgroundResource(
                                    StaticData.getIdForColor(StaticTripData.getColorById(packingobject.getPersonsAssigned()[i])
                                    ));
                }
                else if(i==2){
                    holder.icon3.setVisibility(View.VISIBLE);
                    ((TextView)holder.icon3.findViewById(R.id.fragment_packing_list_item_icon_initial_3))
                            .setText(StaticTripData.getNameById(packingobject.getPersonsAssigned()[i]).substring(0,1));
                    ((ImageView) holder.icon3.findViewById(R.id.fragment_packing_list_item_icon_circle_3))
                            .setBackgroundResource(
                                    StaticData.getIdForColor(StaticTripData.getColorById(packingobject.getPersonsAssigned()[i])
                                    ));
                }
                else if(i==3){
                    holder.icon3.setVisibility(View.VISIBLE);
                    ((TextView)holder.icon3.findViewById(R.id.fragment_packing_list_item_icon_initial_3))
                            .setText(StaticTripData.getNameById(packingobject.getPersonsAssigned()[i]).substring(0,1));
                    ((ImageView) holder.icon3.findViewById(R.id.fragment_packing_list_item_icon_circle_3))
                            .setBackgroundResource(
                                    StaticData.getIdForColor(StaticTripData.getColorById(packingobject.getPersonsAssigned()[i])
                                    ));
                }
            }
        }
        return convertView;
    }

    static class PackingViewHolder{
        ImageView check;
        TextView itemsPacked, title;
        FrameLayout icon1, icon2, icon3, icon4;
    }
}
