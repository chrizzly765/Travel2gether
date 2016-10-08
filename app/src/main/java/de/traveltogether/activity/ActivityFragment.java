package de.traveltogether.activity;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import de.traveltogether.R;
import de.traveltogether.activity.detailactivity.DetailActivityActivity;
import de.traveltogether.mainmenu.MainActivity;
import de.traveltogether.model.Activity;
import de.traveltogether.model.Trip;


public class ActivityFragment extends ListFragment implements AdapterView.OnItemClickListener {

    ActivityAdapter adapter;
    Activity[] formerActivities;
    View view;
    IActivityPresenter presenter;
    long tripId;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ActivityFragment() {

    }

    public static ActivityFragment newInstance(Activity[] activities, IActivityPresenter _presenter, long _tripId) {
        ActivityFragment fragment = new ActivityFragment();
        fragment.formerActivities = activities;
        fragment.presenter=_presenter;
        fragment.tripId = _tripId;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container,savedInstanceState);
        view = inflater.inflate(R.layout.fragment_activity_list, container, false);
        return view;
    }

    public void onStart(){
        super.onStart();
        if(formerActivities==null || formerActivities.length==0 ){
            //TODO: show new trip listitem
        }
        else {
            adapter = new ActivityAdapter(getActivity(), formerActivities);
            setListAdapter(adapter);
            getListView().setOnItemClickListener(this);
        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Activity activity = (Activity) adapter.getItem(position);
        Intent intent = new Intent(getActivity(), DetailActivityActivity.class);
        intent.putExtra("featureId", activity.getId()); //
        /*
        Bundle b = new Bundle();
        b.putString("title", formerActivities[position].getTitle());
        detailActivity.putExtras(b); //Put your id to your next Intent
        */
        startActivity(intent);

    }
}
