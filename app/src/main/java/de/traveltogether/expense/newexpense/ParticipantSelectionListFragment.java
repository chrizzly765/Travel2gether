package de.traveltogether.expense.newexpense;

import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.traveltogether.R;
import de.traveltogether.model.Payer;

import java.util.ArrayList;

/**
 * A fragment representing a list of participants chosen
 */
public class ParticipantSelectionListFragment extends ListFragment {

    private ParticipantSelectionListAdapter adapter;
    private Payer[] payer;
    private View view;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ParticipantSelectionListFragment() {

    }

    public static ParticipantSelectionListFragment newInstance(ArrayList<Payer> _payer ) {
        ParticipantSelectionListFragment fragment = new ParticipantSelectionListFragment();
        Payer[] array = new Payer[_payer.size()];

        for(int i = 0; i<_payer.size(); i++){
            array[i] = _payer.get(i);
        }
        fragment.payer = array;
        return fragment;
    }

    public void refresh(ArrayList<Payer> _payer){
        Payer[] array = new Payer[_payer.size()];

        for(int i = 0; i<_payer.size(); i++){
            array[i] = _payer.get(i);
        }
        payer = array;
        if(adapter == null){
            adapter = new ParticipantSelectionListAdapter(getActivity(), payer);
        }
        else {
            adapter.refresh(array);
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
        if(!(payer==null || payer.length==0 )){
            adapter = new ParticipantSelectionListAdapter(getActivity(), payer);
            setListAdapter(adapter);
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
