package de.traveltogether.packinglist.newpackingitem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.StaticTripData;
import de.traveltogether.model.PackingItem;

import java.text.DecimalFormat;

/**
 * Adapter for selecting persons who pack items
 */
class PackingItemSelectionAdapter extends BaseAdapter {
    private PackingItem[] packingItems;
    private final LayoutInflater inflater;

    public PackingItemSelectionAdapter(Context context, PackingItem[] _packingItem) {
        inflater = LayoutInflater.from(context);
        packingItems = _packingItem;
    }

    public void refresh(PackingItem[] _packingItem){
        packingItems = _packingItem;
    }

    @Override
    public int getCount() {
        return packingItems.length;
    }

    @Override
    public Object getItem(int position) {
        if(packingItems.length>0) {
            return packingItems[position];
        }
        else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        if(packingItems.length>0) {
            return packingItems[position].getAssignedPerson();
        }
        else{
            return -1;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PackingItemSelectionViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_packingitem_list_item, parent, false);
            holder = new PackingItemSelectionViewHolder();
            holder.name = (TextView)convertView.findViewById(R.id.fragment_packingitem_list_item_name);
            holder.initial=(TextView)convertView.findViewById(R.id.fragment_packingitem_list_item_initial);
            holder.icon=(ImageView)convertView.findViewById(R.id.fragment_packingitem_list_item_icon);
            convertView.setTag(holder);
        }
        else{
            holder = (PackingItemSelectionViewHolder) convertView.getTag();
        }

        PackingItem packingItem = (PackingItem) getItem(position);
        holder.name.setText(StaticTripData.getNameById(packingItem.getAssignedPerson()));
        holder.initial.setText(StaticTripData.getNameById(packingItem.getAssignedPerson()).substring(0,1));
        holder.icon.setBackgroundResource(StaticData.getIdForColor(StaticTripData.getColorById(packingItem.getAssignedPerson()))); // TODO: set color!
        return convertView;
    }


    public class PackingItemSelectionViewHolder {
        public TextView name;
        public TextView initial;
        public ImageView icon;
    }
}
