package de.traveltogether.expense.detailexpense;

        import android.content.Context;
        import android.graphics.Color;
        import android.util.Log;
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
        import de.traveltogether.model.Payer;

        import java.text.DecimalFormat;

class ExpenseDetailAdapter extends BaseAdapter {
    private Payer[] payers;
    private LayoutInflater inflater;

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
            holder.icon = (FrameLayout)convertView.findViewById(R.id.fragment_expense_detail_item_icon);
            convertView.setTag(holder);
        }
        else{
            holder = (ExpenseDetailViewHolder) convertView.getTag();
        }

        Context context = parent.getContext();
        Payer payer = (Payer) getItem(position);
        Log.d("payer id", String.valueOf(payer.getId()) + " " + StaticTripData.getNameById(payer.getId()));
        holder.name.setText(StaticTripData.getNameById(payer.getId()));
        String amount = "";
        DecimalFormat df = new DecimalFormat(StaticData.currencyFormatDE);
        amount = "- " + String.valueOf(df.format(payer.getAmount())) + " " + StaticData.currencySymbolDE; //getRe (R.array.currencies)[expense.getCurrencyId()].substring(0,1))
        holder.amount.setTextColor(Color.RED);
        ((TextView)holder.icon.findViewById(R.id.fragment_expense_detail_item_icon_initial)).setText(holder.name.getText().toString().substring(0,1));
        ((ImageView)holder.icon.findViewById(R.id.fragment_expense_detail_item_icon_circle)).setBackgroundResource(StaticData.getIdForColor(StaticTripData.getColorById(payer.getId())));
        holder.amount.setText(amount);
        return convertView;
    }

    public class ExpenseDetailViewHolder {
        TextView name, amount;
        FrameLayout icon;
    }
}
