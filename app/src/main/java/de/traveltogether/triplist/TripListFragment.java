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
import de.traveltogether.triplist.dummy.DummyContent.DummyItem;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TripListFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private TripAdapter adapter;
    private Trip[] trips;
    private OnListFragmentInteractionListener mListener;
    View view;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TripListFragment() {

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
        if(trips.length==0 ||trips==null){
            //TODO: show new trip listitem
        }
        else {
            adapter = new TripAdapter(getActivity(), trips);
            setListAdapter(adapter);
            getListView().setOnItemClickListener(this);
        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Trip trip = (Trip) adapter.getItem(position);
        Intent mainMenu = new Intent(getActivity(), MainActivity.class);
        Bundle b = new Bundle();
        b.putLong("tripId", trips[position].getTripId()); //Your id
        b.putString("title", trips[position].getTitle());
        b.putString("adminId", trips[position].getAdminId());
        mainMenu.putExtras(b); //Put your id to your next Intent
        startActivity(mainMenu);
    }
}
