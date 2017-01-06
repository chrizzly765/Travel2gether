package de.traveltogether.expense.detailexpense;

import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.traveltogether.R;
import de.traveltogether.expense.detailexpense.ExpenseDetailAdapter;
import de.traveltogether.model.Payer;

import java.util.List;


public class ExpenseDetailFragment extends ListFragment {
    private List<Payer> payers;
    private ExpenseDetailAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ExpenseDetailFragment() {
    }

    public static ExpenseDetailFragment newInstance(List<Payer> _payers) {
        ExpenseDetailFragment fragment = new ExpenseDetailFragment();
        fragment.payers=_payers;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense_detail, container, false);
        return view;
    }

    public void onStart(){
        super.onStart();
        if(payers==null || payers.size()==0 ){
            //TODO: show new trip listitem
        }
        else {
            Payer[] payerArray = new Payer[payers.size()];
            for(int i = 0; i < payers.size(); i++){
                payerArray[i] = payers.get(i);
            }
            adapter = new ExpenseDetailAdapter(getActivity(),payerArray);
            setListAdapter(adapter);
        }

    }
}
