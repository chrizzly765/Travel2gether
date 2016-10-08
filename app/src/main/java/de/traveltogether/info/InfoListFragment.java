package de.traveltogether.info;

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
import de.traveltogether.model.Participant;

import java.util.List;

public class InfoListFragment extends ListFragment {
    InfoParticipantAdapter adapter;
    Participant[] participants;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public InfoListFragment() {
    }


    public static InfoListFragment newInstance(Participant[] _participants) {
        InfoListFragment fragment = new InfoListFragment();
        fragment.participants = _participants;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info_list, container, false);
    }

    public void onStart(){
        super.onStart();
        if(participants==null || participants.length==0 ){
            //TODO: Error
        }
        else {
            adapter = new InfoParticipantAdapter(getActivity(), participants);
            setListAdapter(adapter);

            if (adapter == null) {
                return;
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

    }

}
