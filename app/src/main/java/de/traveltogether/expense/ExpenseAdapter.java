package de.traveltogether.expense;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.StaticTripData;
import de.traveltogether.model.Expense;
import de.traveltogether.model.Payer;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
            holder.amount=(TextView)convertView.findViewById(R.id.fragment_expense_list_item_amount);
            holder.iconLast = (FrameLayout)convertView.findViewById(R.id.fragment_expense_list_item_icon_last);
            holder.icon3 = (FrameLayout)convertView.findViewById(R.id.fragment_expense_list_item_icon_3);
            holder.icon2 = (FrameLayout)convertView.findViewById(R.id.fragment_expense_list_item_icon_2);
            holder.icon1 = (FrameLayout)convertView.findViewById(R.id.fragment_expense_list_item_icon_1);
            convertView.setTag(holder);
        }
        else{
            holder = (ExpenseViewHolder) convertView.getTag();
        }

        Context context = parent.getContext();
        Expense expense = (Expense) getItem(position);
        holder.title.setText(expense.getTitle());
        DecimalFormat df = new DecimalFormat(StaticData.currencyFormatDE);
        String amount = String.valueOf(df.format(expense.getAmount())) + " " + StaticData.currencySymbolDE; //getRe (R.array.currencies)[expense.getCurrencyId()].substring(0,1))
                holder.amount.setText(amount);
        if(expense.getAssignedPayers().size()>0){
            ArrayList<Payer> payers = (ArrayList<Payer>)expense.getAssignedPayers();

            holder.iconLast.setVisibility(View.VISIBLE);
            ((TextView) holder.iconLast.findViewById(R.id.fragment_expense_list_item_icon_initial_last))
                    .setText(StaticTripData.getNameById(expense.getPayer()).substring(0, 1));
            ((ImageView) holder.iconLast.findViewById(R.id.fragment_expense_list_item_icon_circle_last))
                    .setBackgroundResource(StaticData.getIdForColor(StaticTripData.getColorById(expense.getPayer())));
            ((ImageView)holder.iconLast.findViewById(R.id.fragment_expense_list_item_payer_icon_circle_last))
                    .setBackgroundResource(StaticData.getIdForColor(StaticTripData.getColorById(expense.getPayer())));
            ((FrameLayout)holder.iconLast.findViewById(R.id.fragment_expense_list_item_payer_icon_last))
                        .setVisibility(View.VISIBLE);

            for(int i = 0; i<payers.size(); i++){
                if(payers.get(i).getId() == expense.getPayer()){
                    break;
                }
                if(i==0){
                    if(payers.size()>3){
                        holder.icon1.findViewById(R.id.fragment_expense_list_item_icon_dotdotdot_1).setVisibility(View.VISIBLE);
                        ((ImageView) holder.icon1.findViewById(R.id.fragment_expense_list_item_icon_circle_1))
                                .setBackgroundResource(R.drawable.circle_light_grey);
                        holder.icon1.findViewById(R.id.fragment_expense_list_item_icon_initial_1).setVisibility(View.INVISIBLE);
                    }
                    else {
                        holder.icon1.setVisibility(View.VISIBLE);
                        ((TextView) holder.icon1.findViewById(R.id.fragment_expense_list_item_icon_initial_1)).setText(StaticTripData.getNameById(payers.get(i).getId()).substring(0, 1));
                        ((ImageView) holder.icon1.findViewById(R.id.fragment_expense_list_item_icon_circle_1)).setBackgroundResource(StaticData.getIdForColor(StaticTripData.getColorById(payers.get(i).getId())));
                    }
                }
                else if(i==1){
                    holder.icon2.setVisibility(View.VISIBLE);
                    ((TextView)holder.icon2.findViewById(R.id.fragment_expense_list_item_icon_initial_2))
                            .setText(StaticTripData.getNameById(payers.get(i).getId()).substring(0,1));
                    ((ImageView)holder.icon2.findViewById(R.id.fragment_expense_list_item_icon_circle_2))
                            .setBackgroundResource(StaticData.getIdForColor(StaticTripData.getColorById(payers.get(i).getId())));


                }else if(i==2){
                    holder.icon3.setVisibility(View.VISIBLE);
                        ((TextView)holder.icon3.findViewById(R.id.fragment_expense_list_item_icon_initial_3))
                                .setText(StaticTripData.getNameById(payers.get(i).getId()).substring(0,1));
                        ((ImageView)holder.icon3.findViewById(R.id.fragment_expense_list_item_icon_circle_3))
                                .setBackgroundResource(StaticData.getIdForColor(StaticTripData.getColorById(payers.get(i).getId())));
                    }
            }

        }
        return convertView;
    }

    public class ExpenseViewHolder {
        TextView title, amount;
        FrameLayout icon1, icon2, icon3, iconLast;
    }
}
