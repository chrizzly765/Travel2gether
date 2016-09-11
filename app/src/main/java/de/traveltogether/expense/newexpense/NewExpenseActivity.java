package de.traveltogether.expense.newexpense;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import de.traveltogether.R;
import de.traveltogether.model.Participant;

public class NewExpenseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    INewExpensePresenter presenter;
    EditText title;
    EditText amount;
    EditText description;
    View currencySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);

        presenter= new NewExpensePresenter(this);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_currency);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currencies, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        title= (EditText)findViewById(R.id.activity_new_expense_title);
        description= (EditText)findViewById(R.id.activity_new_expense_description);
        amount = (EditText)findViewById(R.id.activity_new_expense_amount);
        currencySpinner = (View)findViewById(R.id.spinner_currency);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        parent.getItemAtPosition(0);
    }

    public void onViewParticipants(Participant[] participants){

        Spinner spinner = (Spinner) findViewById(R.id.spinner_paid_by);
        ParticipantSpinnerAdapter adapter = new ParticipantSpinnerAdapter(this, android.R.id.text1, participants);
        adapter.setDropDownViewResource(R.layout.spinner_participants);
        spinner.setAdapter(adapter);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ParticipantSelectionListFragment fragment = ParticipantSelectionListFragment.newInstance(participants);
        fragmentTransaction.add(R.id.activity_new_expense_listcontainer, fragment);
        fragmentTransaction.commit();
    }

    public void onViewError(String message) {
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

    public void onCreateExpense(){
        //presenter.onCreateExpense()
    }

}
