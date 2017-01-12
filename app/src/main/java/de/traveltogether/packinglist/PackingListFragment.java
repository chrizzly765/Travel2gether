package de.traveltogether.packinglist;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import de.traveltogether.R;
import de.traveltogether.model.PackingObject;
import de.traveltogether.packinglist.packingdetail.PackingDetailActivity;

/**
 * Fragment that contains list of packingitems
 */
public class PackingListFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private PackingObject[] packingObjects;
    private PackingListAdapter adapter;
    private View view;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PackingListFragment() {
    }

    public static PackingListFragment newInstance(PackingObject[] _packingobjects) {
        PackingListFragment fragment = new PackingListFragment();
        fragment.packingObjects = _packingobjects;
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
        if(!(packingObjects==null || packingObjects.length==0) ){

            adapter = new PackingListAdapter(getActivity(), packingObjects);
            setListAdapter(adapter);
            getListView().setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PackingObject packingObject = (PackingObject) adapter.getItem(position);

        Intent intent = new Intent(getActivity(), PackingDetailActivity.class);
        Bundle b = new Bundle();
        b.putLong("featureId", packingObject.getId());
        intent.putExtras(b);
        startActivity(intent);
    }
}
