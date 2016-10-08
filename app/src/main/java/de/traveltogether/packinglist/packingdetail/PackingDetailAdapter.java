package de.traveltogether.packinglist.packingdetail;

import android.content.Context;
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

import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.model.PackingItem;

import java.util.List;

public class PackingDetailAdapter extends BaseAdapter {

    PackingItem[] items;
    LayoutInflater inflater;

    public PackingDetailAdapter(Context context, PackingItem[] _items) {
        inflater = LayoutInflater.from(context);
        items = _items;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return items[position].getAssignedPerson();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PackingDetailViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_packing_detail_list_item, parent, false);
            holder = new PackingDetailViewHolder();
            holder.name = (TextView)convertView.findViewById(R.id.fragment_packing_detail_list_item_name);
            holder.box = (CheckBox)convertView.findViewById(R.id.fragment_packing_detail_list_item_checkBox);
            holder.icon = (FrameLayout)convertView.findViewById(R.id.fragment_packing_detail_list_item_icon);

            convertView.setTag(holder);
        }
        else{
            holder = (PackingDetailViewHolder) convertView.getTag();
        }

        Context context = parent.getContext();
        PackingItem item = (PackingItem) getItem(position);
        holder.name.setText(StaticData.getNameById(item.getAssignedPerson()));
        holder.box.setActivated(item.getStatus());
        if(item.getAssignedPerson() == StaticData.getUserId()){
            holder.box.setEnabled(true);
        }
        ((TextView)holder.icon.findViewById(R.id.fragment_packing_detail_list_item_initial)).setText(holder.name.getText().toString().substring(0,1));
        ((ImageView)holder.icon.findViewById(R.id.fragment_packing_detail_list_item_circle)).setBackgroundResource(StaticData.getIdForColor(StaticData.getColorById(item.getAssignedPerson())));
        String amount = "";

        return convertView;
    }

    public class PackingDetailViewHolder {
        TextView name;
        CheckBox box;
        FrameLayout icon;
    }
}
