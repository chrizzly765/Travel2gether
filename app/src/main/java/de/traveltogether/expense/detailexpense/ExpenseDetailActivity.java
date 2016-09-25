package de.traveltogether.expense.detailexpense;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.expense.ExpenseListFragment;
import de.traveltogether.expense.newexpense.NewExpenseActivity;
import de.traveltogether.model.Expense;
import de.traveltogether.model.Payer;

public class ExpenseDetailActivity extends AppCompatActivity {
    long featureId;
    IExpenseDetailPresenter presenter;
    TextView title;
    TextView description;
    TextView amount;
    TextView paidBy;
    ProgressDialog progressDialog;

    Expense expense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ExpenseDetailPresenter(this);
        setContentView(R.layout.activity_expense_detail);
        featureId = getIntent().getLongExtra("featureId", -1);
        if(featureId!=-1){
            presenter.onGetDetailsForExpense(featureId);
        }
        title = (TextView) findViewById(R.id.activity_expense_detail_title);
        description = (TextView) findViewById(R.id.activity_expense_detail_description);
        amount = (TextView) findViewById(R.id.activity_expense_detail_amount);
        paidBy = (TextView)findViewById(R.id.activity_expense_detail_paidby);
        progressDialog = ProgressDialog.show(this, "",
                "Bitte warten...", true);
    }

    public void onViewError(String message){
        progressDialog.cancel();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(getString(R.string.error));
        builder.setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onViewDetails(Expense _expense){
        expense = _expense;
        getSupportActionBar().setTitle(expense.getTitle());
        title.setText(expense.getTitle());
        description.setText(expense.getDescription());
        amount.setText(expense.getAmount() + getResources().getStringArray(R.array.currencies)[expense.getCurrencyId()].substring(0,1));
        paidBy.setText(StaticData.getNameById(expense.getPayer()));

        onViewPayers(expense.getAssignedPayers());

    }

    public void onViewPayers(List<Payer> payerList){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ExpenseDetailFragment fragment = ExpenseDetailFragment.newInstance(payerList);
        fragmentTransaction.add(R.id.activity_expense_detail_list_container, fragment);
        fragmentTransaction.commit();

        progressDialog.cancel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionsmenu_detail, menu);
        if(expense!=null) {
            if (expense.getAuthor() == StaticData.getUserId()) {
                menu.getItem(R.id.delete).setEnabled(true);
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                if(expense.getAuthor() == StaticData.getUserId()){
                    presenter.onDeleteExpense(expense.getId());
                }
                break;
            case R.id.edit:
                Intent intent = new Intent(this, NewExpenseActivity.class);
                intent.putExtra("featureId", expense.getId());
                startActivity(intent);
                break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }
        return  true;
    }

    public void onSuccessDelete(){
        //TODO: toast
        finish();
    }


}
