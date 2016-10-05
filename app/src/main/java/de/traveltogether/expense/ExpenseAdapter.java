package de.traveltogether.expense;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import de.traveltogether.R;
import de.traveltogether.model.Expense;

import java.text.DecimalFormat;
import java.util.List;

public class ExpenseAdapter extends BaseAdapter {
    Expense[] expenses;
    LayoutInflater inflater;

    public ExpenseAdapter(Context context, Expense[] _expenses) {
        inflater = LayoutInflater.from(context);
        expenses = _expenses;
    }

    @Override
    public int getCount() {
        return expenses.length;
    }

    @Override
    public Object getItem(int position) {
        return expenses[position];
    }

    @Override
    public long getItemId(int position) {
        return expenses[position].getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ExpenseViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_expense_list_item, parent, false);
            holder = new ExpenseViewHolder();
            holder.title = (TextView)convertView.findViewById(R.id.fragment_expense_list_item_title);
            //holder.description= (TextView)convertView.findViewById(R.id.fragment_trip_list_item_description);
            holder.amount=(TextView)convertView.findViewById(R.id.fragment_expense_list_item_amount);
            convertView.setTag(holder);
        }
        else{
            holder = (ExpenseViewHolder) convertView.getTag();
        }

        Context context = parent.getContext();
        Expense expense = (Expense) getItem(position);
        holder.title.setText(expense.getTitle());
        //holder.description.setText(trip.getDescription());
        DecimalFormat df = new DecimalFormat("#0.00");
        String amount = String.valueOf(df.format(expense.getAmount())) + " €"; //getRe (R.array.currencies)[expense.getCurrencyId()].substring(0,1))
                holder.amount.setText(amount);
        return convertView;
    }

    public class ExpenseViewHolder {
        TextView title, amount;
    }
}
