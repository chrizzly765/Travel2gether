package de.traveltogether.expense.detailexpense;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringEscapeUtils;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;

import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.StaticTripData;
import de.traveltogether.dialog.DeleteActivity;
import de.traveltogether.expense.ExpenseActivity;
import de.traveltogether.expense.ExpenseListFragment;
import de.traveltogether.expense.newexpense.NewExpenseActivity;
import de.traveltogether.model.Expense;
import de.traveltogether.model.Payer;

public class ExpenseDetailActivity extends DeleteActivity{
    long featureId =-1;
    long tripId =-1;
    IExpenseDetailPresenter presenter;
    TextView title;
    TextView description;
    TextView amount;
    TextView paidBy;
    ProgressDialog progressDialog;
    ExpenseDetailFragment payersFragment;
    Expense expense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);

        getSupportActionBar().setTitle("Ausgabe");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setLogo(R.mipmap.logo_ohne_schrift);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        presenter = new ExpenseDetailPresenter(this);
        featureId = getIntent().getLongExtra("featureId", -1);
        tripId = getIntent().getLongExtra("tripId", -1);
        if(tripId!=-1){
            StaticTripData.setCurrentTripId(tripId);
        }

        title = (TextView) findViewById(R.id.   activity_expense_detail_title);
        description = (TextView) findViewById(R.id.activity_expense_detail_description);
        amount = (TextView) findViewById(R.id.activity_expense_detail_amount);
        paidBy = (TextView)findViewById(R.id.activity_expense_detail_paidby);

    }

    @Override
    protected void onStart(){
        super.onStart();
        progressDialog = ProgressDialog.show(this, "",
                "Bitte warten...", true);
        if(featureId!=-1){
            presenter.onGetDetailsForExpense(featureId);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.detach(payersFragment);
        fragmentTransaction.commit();
        super.onSaveInstanceState(outState);
    }

    public void onViewError(String message, String title){
        progressDialog.cancel();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onViewDetails(Expense _expense){

        DecimalFormat df = new DecimalFormat(StaticData.currencyFormatDE);

        expense = _expense;
        title.setText(StringEscapeUtils.unescapeJava(expense.getTitle()));
        tripId=expense.getTripId();
        getSupportActionBar().setTitle(StringEscapeUtils.unescapeJava(expense.getTitle()));
        description.setText(StringEscapeUtils.unescapeJava(expense.getDescription()));
        amount.setText(df.format(expense.getAmount()) + getResources().getStringArray(R.array.currencies)[expense.getCurrencyId()].substring(0,1));
        paidBy.setText(StaticTripData.getNameById(expense.getPayer()));

        FrameLayout icon = (FrameLayout)findViewById(R.id.activity_expense_detail_icon);
        ((ImageView)icon.findViewById(R.id.activity_expense_detail_icon_circle))
                .setBackgroundResource(StaticData.getIdForColor(StaticTripData.getColorById(expense.getPayer())));
        ((ImageView)icon.findViewById(R.id.activity_expense_detail_payer_icon_circle))
                .setBackgroundResource(StaticData.getIdForColor(StaticTripData.getColorById(expense.getPayer())));
        ((TextView)icon.findViewById(R.id.activiy_expense_detail_icon_initial))
                .setText(StaticTripData.getNameById(expense.getPayer()).substring(0,1));

        onViewPayers(expense.getAssignedPayers());

    }

    public void onViewPayers(List<Payer> payerList){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        payersFragment = ExpenseDetailFragment.newInstance(payerList);
        fragmentTransaction.add(R.id.activity_expense_detail_list_container, payersFragment);
        fragmentTransaction.commit();

        progressDialog.cancel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionsmenu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                createDeleteDialog();
                /*if(expense.getAuthor() == StaticData.getUserId()){
                    presenter.onDeleteExpense(expense.getId());
                }
                else{
                    onViewError("Nur der Ersteller dieser Ausgabe darf die Ausgabe löschen.");
                }*/
                break;
            case R.id.edit:
                Intent intent = new Intent(this, NewExpenseActivity.class);
                Bundle b = new Bundle();
                b.putLong("featureId", featureId);
                b.putLong("tripId", tripId);
                intent.putExtras(b);
                startActivity(intent);
                break;
            case android.R.id.home:
                finish();
                return true;
            default:
                super.onOptionsItemSelected(item);
                break;
        }
        return  true;
    }

    public void onSuccessDelete(){
        Context context = getApplicationContext();
        CharSequence text = "Ausgabe erfolgreich gelöscht.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        finish();
    }


    public void onCloseActivity(){
        progressDialog.cancel();

        Context context = getApplicationContext();
        CharSequence text = "Diese Ausgabe wurde gelöscht.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        finish();
    }

    public void delete(){
        if(expense.getAuthor() == StaticData.getUserId()){
            presenter.onDeleteExpense(expense.getId());
        }
        else{
            onViewError("Nur der Ersteller dieser Ausgabe darf die Ausgabe löschen.", "Sorry.");
        }
    }
}
