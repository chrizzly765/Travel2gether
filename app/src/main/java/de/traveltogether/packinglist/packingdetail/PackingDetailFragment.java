package de.traveltogether.packinglist.packingdetail;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.traveltogether.R;
import de.traveltogether.model.PackingItem;


import java.util.List;


public class PackingDetailFragment extends ListFragment {

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
            PackingItem[] payerArray = new PackingItem[items.size()];
            for(int i = 0; i < items.size(); i++){
                payerArray[i] = items.get(i);
            }
            adapter = new PackingDetailAdapter(getActivity(),payerArray);
            setListAdapter(adapter);
        }

    }


}
