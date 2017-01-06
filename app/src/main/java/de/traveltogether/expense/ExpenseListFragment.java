package de.traveltogether.expense;

import android.content.Intent;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import de.traveltogether.R;
import de.traveltogether.expense.detailexpense.ExpenseDetailActivity;
import de.traveltogether.model.Expense;


public class ExpenseListFragment extends ListFragment implements AdapterView.OnItemClickListener {
    private Expense[] expenses;
    private ExpenseAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ExpenseListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ExpenseListFragment newInstance(Expense[] _expenses) {
        ExpenseListFragment fragment = new ExpenseListFragment();
        fragment.expenses=_expenses;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense_list, container, false);
        return view;
    }

    public void onStart(){
        super.onStart();
        if(expenses==null || expenses.length==0 ){
            getActivity().findViewById(R.id.activity_expense_empty).setVisibility(View.VISIBLE);
        }
        else {
            getActivity().findViewById(R.id.activity_expense_empty).setVisibility(View.INVISIBLE);
            adapter = new ExpenseAdapter(getActivity(),expenses);
            setListAdapter(adapter);
            getListView().setOnItemClickListener(this);


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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Expense expense = (Expense) adapter.getItem(position);
        Intent intent = new Intent(getActivity(), ExpenseDetailActivity.class);
        Bundle b = new Bundle();
        b.putLong("featureId", expense.getId());
        intent.putExtras(b);
        startActivity(intent);
    }
}
