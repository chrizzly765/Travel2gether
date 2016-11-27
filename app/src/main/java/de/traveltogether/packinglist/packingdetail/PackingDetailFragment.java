package de.traveltogether.packinglist.packingdetail;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import de.traveltogether.R;
import de.traveltogether.model.PackingItem;


import java.util.List;


public class PackingDetailFragment extends ListFragment implements AdapterView.OnItemClickListener {

    List<PackingItem> items;
    PackingDetailAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PackingDetailFragment() {
    }

    public static PackingDetailFragment newInstance(List<PackingItem> _items) {
        PackingDetailFragment fragment = new PackingDetailFragment();
        fragment.items=_items;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_packing_detail_list, container, false);
        return view;
    }

    public void onStart(){
        super.onStart();
        if(items==null || items.size()==0 ){
            //TODO: show new trip listitem
        }
        else {
            PackingItem[] array = new PackingItem[items.size()];
            for(int i = 0; i < items.size(); i++){
                array[i] = items.get(i);
            }
            adapter = new PackingDetailAdapter(getActivity(),array);
            setListAdapter(adapter);
            getListView().setOnItemClickListener(this);
        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("Fragment", "onitemclick");
        if(((PackingItem)adapter.getItem(position)).getStatus() == true){
            ((LinearLayout)view).findViewById(R.id.fragment_packing_detail_list_item_checkBox).setBackgroundResource(R.drawable.checkbox_empty);
        }
        else{
            ((LinearLayout)view).findViewById(R.id.fragment_packing_detail_list_item_checkBox).setBackgroundResource(R.drawable.checkbox_filled);
        }
        ((PackingDetailActivity)getActivity()).setPackingItemClicked(position);
    }
}
