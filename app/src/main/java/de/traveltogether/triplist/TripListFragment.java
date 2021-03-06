package de.traveltogether.triplist;

import android.app.ListFragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import de.traveltogether.R;
import de.traveltogether.mainmenu.MainActivity;
import de.traveltogether.model.Trip;

/**
 * A fragment representing a list of trips.
 */
public class TripListFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private TripAdapter adapter;
    private Trip[] trips;
    private View view;

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
        if(!(trips==null || trips.length==0) ){
            adapter = new TripAdapter(getActivity(), trips);
            setListAdapter(adapter);
            getListView().setOnItemClickListener(this);

            //meassure neccessary height for trip list and set params
            Resources resources = getActivity().getResources();
            DisplayMetrics metrics = resources.getDisplayMetrics();
            int px = (int)(102 * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
            int unbounded = View.MeasureSpec.makeMeasureSpec(px, View.MeasureSpec.AT_MOST);
            ViewGroup vg = getListView();
            int totalHeight = 0;
            for (int i = 0; i < adapter.getCount(); i++) {
                View listItem = adapter.getView(i, null, vg);
                listItem.measure(unbounded, unbounded);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams par = getListView().getLayoutParams();
            par.height = totalHeight + (getListView().getDividerHeight() * (adapter.getCount() - 1));
            getListView().setLayoutParams(par);
            getListView().requestLayout();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent mainMenu = new Intent(getActivity(), MainActivity.class);
        Bundle b = new Bundle();
        b.putLong("tripId", trips[position].getTripId());
        b.putString("title", trips[position].getTitle());
        b.putInt("adminId", trips[position].getAdminId());
        mainMenu.putExtras(b); //Put your data to your next Intent
        startActivity(mainMenu);
    }
}
