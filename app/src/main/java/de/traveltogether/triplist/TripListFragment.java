package de.traveltogether.triplist;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import de.traveltogether.R;
import de.traveltogether.mainmenu.MainActivity;
import de.traveltogether.model.Trip;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class TripListFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private TripAdapter adapter;
    private Trip[] trips;
    View view;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TripListFragment() {

    }

    public void refresh(Trip[] _trips){
        trips=_trips;
        adapter.refresh(trips);
        adapter.notifyDataSetChanged();

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TripListFragment newInstance(Trip[] _trips ) {
        TripListFragment fragment = new TripListFragment();
        fragment.trips = _trips;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trip_list, container, false);

        return view;
    }

    public void onStart(){
        super.onStart();
        if(trips==null || trips.length==0 ){
            //TODO: show new trip listitem
        }
        else {
            adapter = new TripAdapter(getActivity(), trips);
            setListAdapter(adapter);
            getListView().setOnItemClickListener(this);

            ViewGroup vg = getListView();
            int totalHeight = 0;
            for (int i = 0; i < adapter.getCount(); i++) {
                View listItem = adapter.getView(i, null, vg);
                listItem.measure(0, 0);
                totalHeight += 440;//listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams par = getListView().getLayoutParams();
            par.height = totalHeight + (getListView().getDividerHeight() * (adapter.getCount() - 1));
            getListView().setLayoutParams(par);
            getListView().requestLayout();
        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Trip trip = (Trip) adapter.getItem(position);

        Intent mainMenu = new Intent(getActivity(), MainActivity.class);
        Bundle b = new Bundle();
        b.putLong("tripId", trips[position].getTripId()); //Your id
        b.putString("title", trips[position].getTitle());
        b.putInt("adminId", trips[position].getAdminId());
        mainMenu.putExtras(b); //Put your id to your next Intent
        startActivity(mainMenu);
        //getActivity().finish();
    }
}
