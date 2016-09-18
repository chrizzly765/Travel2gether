package de.traveltogether.expense.newexpense;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.traveltogether.R;
import de.traveltogether.model.Participant;
import de.traveltogether.model.Payer;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ParticipantSelectionListFragment extends ListFragment {

    private ParticipantSelectionListAdapter adapter;
    private Payer[] payer;
    View view;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ParticipantSelectionListFragment() {

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ParticipantSelectionListFragment newInstance(ArrayList<Payer> _payer ) {
        ParticipantSelectionListFragment fragment = new ParticipantSelectionListFragment();
        Payer[] array = new Payer[_payer.size()];

        for(int i = 0; i<_payer.size(); i++){
            array[i] = _payer.get(i);
        }
        fragment.payer = array;
        return fragment;
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
        if(payer==null || payer.length==0 ){
            //TODO: show new trip listitem
        }
        else {
            adapter = new ParticipantSelectionListAdapter(getActivity(), payer);
            setListAdapter(adapter);
        }

    }

}
