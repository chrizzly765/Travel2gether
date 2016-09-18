package de.traveltogether.expense.detailexpense;

        import android.content.Context;
        import android.graphics.Color;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.TextView;

        import de.traveltogether.R;
        import de.traveltogether.StaticData;
        import de.traveltogether.model.Expense;
        import de.traveltogether.model.Payer;

        import java.util.List;

public class ExpenseDetailAdapter extends BaseAdapter {
    Payer[] payers;
    LayoutInflater inflater;

    public ExpenseDetailAdapter(Context context, Payer[] _payers) {
        inflater = LayoutInflater.from(context);
        payers = _payers;
    }

    @Override
    public int getCount() {
        return payers.length;
    }

    @Override
    public Object getItem(int position) {
        return payers[position];
    }

    @Override
    public long getItemId(int position) {
        return payers[position].getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ExpenseDetailViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_expense_detail_item, parent, false);
            holder = new ExpenseDetailViewHolder();
            holder.name = (TextView)convertView.findViewById(R.id.fragment_expense_detail_item_name);
            //holder.description= (TextView)convertView.findViewById(R.id.fragment_trip_list_item_description);
            holder.amount=(TextView)convertView.findViewById(R.id.fragment_expense_detail_item_amount);
            convertView.setTag(holder);
        }
        else{
            holder = (ExpenseDetailViewHolder) convertView.getTag();
        }

        Context context = parent.getContext();
        Payer payer = (Payer) getItem(position);
        holder.name.setText(StaticData.getNameById(payer.getId()));
        //holder.description.setText(trip.getDescription());
        String amount = "";
        if(payer.getAmount()>=0){
            amount= "+ " + String.valueOf(payer.getAmount());
            holder.amount.setTextColor(Color.BLACK);
        }
        else {
            amount = "- " + String.valueOf(payer.getAmount());
            holder.amount.setTextColor(Color.RED);
        }
        holder.amount.setText(amount);
        return convertView;
    }

    public class ExpenseDetailViewHolder {
        TextView name, amount;
    }
}
