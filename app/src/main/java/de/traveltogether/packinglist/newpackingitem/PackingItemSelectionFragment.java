package de.traveltogether.packinglist.newpackingitem;

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

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class PackingItemSelectionFragment extends ListFragment {

    private PackingItemSelectionAdapter adapter;
    private PackingItem[] packingItems;
    View view;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PackingItemSelectionFragment() {

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PackingItemSelectionFragment newInstance(ArrayList<PackingItem> _packingItem ) {
        PackingItemSelectionFragment fragment = new PackingItemSelectionFragment();
        PackingItem[] array = new PackingItem[_packingItem.size()];

        for(int i = 0; i<_packingItem.size(); i++){
            array[i] = _packingItem.get(i);
        }
        fragment.packingItems = array;
        return fragment;
    }

    public void refresh(ArrayList<PackingItem> _packingItem){
        PackingItem[] array = new PackingItem[_packingItem.size()];

        for(int i = 0; i<_packingItem.size(); i++){
            array[i] = _packingItem.get(i);
        }
        packingItems = array;
        if(adapter == null){
            adapter = new PackingItemSelectionAdapter(getActivity(), packingItems);
        }
        else {
            adapter.refresh(array);
        }

        ViewGroup vg = getListView();
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = getListView().getLayoutParams();
        par.height = totalHeight + (getListView().getDividerHeight() * (adapter.getCount() - 1));
        getListView().setLayoutParams(par);
        getListView().requestLayout();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_participant_selection_list, container, false);

        return view;
    }

    public void onStart(){
        super.onStart();
        if(packingItems==null || packingItems.length==0 ){
            //TODO: show new trip listitem
        }
        else {
            adapter = new PackingItemSelectionAdapter(getActivity(), packingItems);
            setListAdapter(adapter);


            ViewGroup vg = getListView();
            int totalHeight = 0;
            for (int i = 0; i < adapter.getCount(); i++) {
                View listItem = adapter.getView(i, null, vg);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams par = getListView().getLayoutParams();
            par.height = totalHeight + (getListView().getDividerHeight() * (adapter.getCount() - 1));
            getListView().setLayoutParams(par);
            getListView().requestLayout();
        }
    }

}
