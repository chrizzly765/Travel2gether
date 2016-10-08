package de.traveltogether.activity.detailactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.app.ListFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import de.traveltogether.R;
//import de.traveltogether.expense.detailexpense.ExpenseDetailActivity;
//import de.traveltogether.expense.detailexpense.ExpenseDetailAdapter;
import de.traveltogether.model.Activity;
import de.traveltogether.model.Expense;

import java.util.List;

import java.util.List;

public class DetailActivityFragment extends ListFragment {
    List<Activity> activities;
    DetailActivityAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DetailActivityFragment() {
    }

    public static DetailActivityFragment newInstance(List<Activity> _activities) {
        DetailActivityFragment fragment = new DetailActivityFragment();
        fragment.activities=_activities;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_detail_list, container, false);
        return view;
    }

    public void onStart(){
        super.onStart();

        if(activities==null || activities.size()==0 ){
            //TODO: show new trip listitem
        }
        else {
            Activity[] activityArray = new Activity[activities.size()];
            for(int i = 0; i < activities.size(); i++){
                activityArray[i] = activities.get(i);
            }

            adapter = new DetailActivityAdapter(getActivity(),activityArray);
            setListAdapter(adapter);
        }

    }
}
