package de.traveltogether.expense;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import de.traveltogether.R;
import de.traveltogether.model.Expense;
import de.traveltogether.model.Participant;

import java.util.List;


public class ExpenseParticipantFragment extends ListFragment{
    Participant[] participants;
    ExpenseParticipantAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ExpenseParticipantFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ExpenseParticipantFragment newInstance(Participant[] _participants) {
        ExpenseParticipantFragment fragment = new ExpenseParticipantFragment();
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
        View view = inflater.inflate(R.layout.fragment_expense_participant_list, container, false);
        return view;
    }

    public void onStart(){
        super.onStart();
        if(participants==null || participants.length==0 ){
            //TODO: show new trip listitem
        }
        else {
            adapter = new ExpenseParticipantAdapter(getContext(),participants);
            setListAdapter(adapter);
        }

    }

}
