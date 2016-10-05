package de.traveltogether.packinglist;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import de.traveltogether.R;
import de.traveltogether.mainmenu.MainActivity;
import de.traveltogether.model.PackingObject;

import java.util.List;



/**
 * A fragment representing a list of Items.
 * <p>
 * interface.
 */
public class PackingFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private PackingAdapter adapter;
    private PackingObject[] packingobjects;
    View view;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PackingFragment() {

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PackingFragment newInstance(PackingObject[] _packingobject) {
        PackingFragment fragment = new PackingFragment();
        fragment.packingobjects = _packingobject;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_packing_list, container, false);
        return view;
    }

    public void onStart(){
        super.onStart();
        if(packingobjects==null || packingobjects.length == 0 ){
            //TODO: show new trip listitem
        }
        else {
            adapter = new PackingAdapter(getActivity(), packingobjects);
            setListAdapter(adapter);
            getListView().setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        PackingObject packingobject = (PackingObject) adapter.getItem(position);
        Intent mainMenu = new Intent(getActivity(), MainActivity.class);
        Bundle b = new Bundle();
        b.putLong("featureId", packingobjects[position].getId());
        b.putString("title", packingobjects[position].getTitle());
        mainMenu.putExtras(b); //Put your id to your next Intent
        startActivity(mainMenu);
    }
}
