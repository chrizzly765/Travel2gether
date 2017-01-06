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


            /*ViewGroup vg = getListView();
            int totalHeight = 0;
            for (int i = 0; i < adapter.getCount(); i++) {
                View listItem = adapter.getView(i, null, vg);
                listItem.measure(0, 0);
                totalHeight += 440;//listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams par = getListView().getLayoutParams();
            par.height = totalHeight + (getListView().getDividerHeight() * (adapter.getCount() - 1));
            getListView().setLayoutParams(par);
            getListView().requestLayout();*/
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("click", "on item click");
        PackingObject packingObject = (PackingObject) adapter.getItem(position);

        Intent intent = new Intent(getActivity(), PackingDetailActivity.class);
        Bundle b = new Bundle();
        b.putLong("featureId", packingObject.getId()); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
    }
}
