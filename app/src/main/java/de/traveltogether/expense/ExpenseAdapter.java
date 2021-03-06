package de.traveltogether.expense;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.lang3.StringEscapeUtils;

import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.StaticTripData;
import de.traveltogether.model.Expense;
import de.traveltogether.model.Payer;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Adapter for list of expenses
 * Brings together views and data called from server
 */
class ExpenseAdapter extends BaseAdapter {
    private Expense[] expenses;
    private LayoutInflater inflater;

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
        holder.title.setText(StringEscapeUtils.unescapeJava(expense.getTitle()));
        DecimalFormat df = new DecimalFormat(StaticData.currencyFormatDE);
        String amount = String.valueOf(df.format(expense.getAmount())) + " " + StaticData.currencySymbolDE; //getRe (R.array.currencies)[expense.getCurrencyId()].substring(0,1))
                holder.amount.setText(amount);

        holder.iconLast.setVisibility(View.VISIBLE);
        ((TextView) holder.iconLast.findViewById(R.id.fragment_expense_list_item_icon_initial_last))
                .setText(StaticTripData.getNameById(expense.getPayer()).substring(0, 1));
        holder.iconLast.findViewById(R.id.fragment_expense_list_item_icon_circle_last)
                .setBackgroundResource(StaticData.getIdForColor(StaticTripData.getColorById(expense.getPayer())));
        holder.iconLast.findViewById(R.id.fragment_expense_list_item_payer_icon_circle_last)
                .setBackgroundResource(StaticData.getIdForColor(StaticTripData.getColorById(expense.getPayer())));
        holder.iconLast.findViewById(R.id.fragment_expense_list_item_payer_icon_last)
                .setVisibility(View.VISIBLE);

        if(expense.getAssignedPayers().size()>0){
            ArrayList<Payer> payers = (ArrayList<Payer>)expense.getAssignedPayers();

            boolean payedByIsPayer = false;
            for(Payer p : payers){
                if(p.getId()==expense.getPayer()){
                    payedByIsPayer = true;
                }
            }

            //view at maximum 4 participant icons
            //if more, view only 3 and a plus sign
            int counter = 0;
            for(int i = 0; i<payers.size(); i++){
                if(payers.get(i).getId() != expense.getPayer()){
                    if (counter == 0) {
                        int count;
                        if (payedByIsPayer)
                            count = 4;
                        else count = 3;

                        if (payers.size() > count) {
                            holder.icon1.setVisibility(View.VISIBLE);
                            holder.icon1.findViewById(R.id.fragment_expense_list_item_icon_dotdotdot_1).setVisibility(View.VISIBLE);
                            holder.icon1.findViewById(R.id.fragment_expense_list_item_icon_circle_1)
                                    .setBackgroundResource(R.drawable.circle_light_grey);
                            holder.icon1.findViewById(R.id.fragment_expense_list_item_icon_initial_1).setVisibility(View.INVISIBLE);
                        } else {
                            holder.icon1.setVisibility(View.VISIBLE);
                            ((TextView) holder.icon1.findViewById(R.id.fragment_expense_list_item_icon_initial_1))
                                    .setText(StaticTripData.getNameById(payers.get(i).getId()).substring(0, 1));
                            (holder.icon1.findViewById(R.id.fragment_expense_list_item_icon_circle_1))
                                    .setBackgroundResource(StaticData.getIdForColor(StaticTripData.getColorById(payers.get(i).getId())));
                        }
                    } else if (counter == 1) {
                        holder.icon2.setVisibility(View.VISIBLE);
                        ((TextView) holder.icon2.findViewById(R.id.fragment_expense_list_item_icon_initial_2))
                                .setText(StaticTripData.getNameById(payers.get(i).getId()).substring(0, 1));
                        (holder.icon2.findViewById(R.id.fragment_expense_list_item_icon_circle_2))
                                .setBackgroundResource(StaticData.getIdForColor(StaticTripData.getColorById(payers.get(i).getId())));


                    } else if (counter == 2) {
                        holder.icon3.setVisibility(View.VISIBLE);
                        ((TextView) holder.icon3.findViewById(R.id.fragment_expense_list_item_icon_initial_3))
                                .setText(StaticTripData.getNameById(payers.get(i).getId()).substring(0, 1));
                        (holder.icon3.findViewById(R.id.fragment_expense_list_item_icon_circle_3))
                                .setBackgroundResource(StaticData.getIdForColor(StaticTripData.getColorById(payers.get(i).getId())));
                    }
                    counter++;

                }
            }

        }
        return convertView;
    }

    /**
     * ViewHolder for an item of expense list
     * Holds all elements needed to be transformed
     */
    public class ExpenseViewHolder {
        TextView title, amount;
        FrameLayout icon1, icon2, icon3, iconLast;
    }
}
