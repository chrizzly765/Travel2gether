package de.traveltogether.packinglist.packingdetail;

import android.content.Context;
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
import de.traveltogether.model.PackingItem;

/**
 * Adapter for list of persons that pack an item
 * Gets packing items after calling them from server and fills views with data
 */
class PackingDetailAdapter extends BaseAdapter {
    private PackingItem[] items;
    private LayoutInflater inflater;

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
            holder.box = (ImageView)convertView.findViewById(R.id.fragment_packing_detail_list_item_checkBox);
            holder.icon = (FrameLayout)convertView.findViewById(R.id.fragment_packing_detail_list_item_icon);
            convertView.setTag(holder);
        }
        else{
            holder = (PackingDetailViewHolder) convertView.getTag();
        }

        PackingItem item = (PackingItem) getItem(position);
        holder.name.setText(StaticTripData.getNameById(item.getAssignedPerson()));
        holder.box.setEnabled(false);

        if(item.getAssignedPerson() == StaticData.getUserId()){
            if(item.getStatus()==true){
                holder.box.setBackgroundResource(R.drawable.checkbox_filled);
            }
            else{
                holder.box.setBackgroundResource(R.drawable.checkbox_empty);
            }
        }
        else{
            if(item.getStatus()==true){
                holder.box.setBackgroundResource(R.drawable.checkbox_filled_grey);
            }
            else{
                holder.box.setBackgroundResource(R.drawable.checkbox_empty_grey);
            }
        }
        ((TextView)holder.icon.findViewById(R.id.fragment_packing_detail_list_item_initial)).setText(holder.name.getText().toString().substring(0,1));
        holder.icon.findViewById(R.id.fragment_packing_detail_list_item_circle).setBackgroundResource(StaticData.getIdForColor(StaticTripData.getColorById(item.getAssignedPerson())));
        return convertView;
    }

    /**
     * Viewholder that contains elements one packer item in packer list
     */
    public class PackingDetailViewHolder {
        TextView name;
        ImageView box;
        FrameLayout icon;
    }
}
