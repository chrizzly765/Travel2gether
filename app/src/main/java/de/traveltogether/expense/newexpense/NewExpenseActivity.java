package de.traveltogether.expense.newexpense;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.expense.ExpenseActivity;
import de.traveltogether.expense.detailexpense.ExpenseDetailPresenter;
import de.traveltogether.model.Expense;
import de.traveltogether.model.Participant;
import de.traveltogether.model.Payer;
import de.traveltogether.notification.NotificationActivity;
import de.traveltogether.settings.SettingsActivity;

public class NewExpenseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{

    INewExpensePresenter presenter;
    EditText title;
    EditText amount;
    EditText description;
    Spinner currencySpinner;
    int currentPayerId;
    ImageButton addButton;
    long tripId;
    long featureId;
    Participant[] participants;
    String[] participantNames;
    ArrayList<Integer> chosenIds = new ArrayList<Integer>();
    ArrayList<Payer> chosenParticipants;
    ProgressDialog progressDialog;
    Switch shareEvenlySwitch;
    ParticipantSelectionListFragment fragment;
    Expense expense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);
        getSupportActionBar().setTitle("Neue Ausgabe");
        Bundle b = getIntent().getExtras();
        if (b != null) {
            tripId = b.getLong("tripId", -1);
            featureId = b.getLong("featureId", -1);
        }

        presenter= new NewExpensePresenter(this);
        if(tripId!=-1) {
            presenter.onGetParticipantsForTrip(tripId);
        }
        else{
            onViewParticipants(StaticData.getActiveParticipants());
        }

        currencySpinner = (Spinner) findViewById(R.id.spinner_currency);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currencies, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(adapter);

        title= (EditText)findViewById(R.id.activity_new_expense_title);
        description= (EditText)findViewById(R.id.activity_new_expense_description);
        amount = (EditText)findViewById(R.id.activity_new_expense_amount);
        //currencySpinner = (View)findViewById(R.id.spinner_currency);
        addButton = (ImageButton)findViewById(R.id.activity_new_expense_button_add);
        addButton.setOnClickListener(this);

        shareEvenlySwitch = (Switch)findViewById(R.id.activity_new_expense_switch);
        if(featureId!=-1){

            presenter.onGetDetailForExpense(featureId);
            progressDialog = ProgressDialog.show(this, "",
                    "Bitte warten...", true);
        }
        if(chosenParticipants == null){
            chosenParticipants = new ArrayList<Payer>();
        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment = ParticipantSelectionListFragment.newInstance(chosenParticipants);
        fragmentTransaction.add(R.id.activity_new_expense_listcontainer, fragment);
        fragmentTransaction.commit();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_save:
                if(title.getText().toString()=="" || amount.getText().toString()==""){
                    onViewError("Bitte gib die Daten vollständig an");
                    return false;
                }
                if(featureId!=-1){
                    expense.setTitle(title.getText().toString());
                    expense.setDescription(description.getText().toString());
                    expense.setPayer(currentPayerId);
                    expense.setAmount(Integer.parseInt(amount.getText().toString()));
                    expense.setAssignedPayers(chosenParticipants);
                    expense.setLastUpdateBy(StaticData.getUserId());


                    presenter.onUpdateExpense(expense);
                }
                else {
                    int currency = currencySpinner.getSelectedItemPosition();
                    if(currencySpinner.getSelectedItem() == null){
                        currencySpinner.getSelectedItem().toString();
                    }
                    Expense expense = new Expense(
                            title.getText().toString(),
                            0,
                            description.getText().toString(),
                            StaticData.getUserId(),
                            Double.parseDouble(amount.getText().toString()),
                            currency,
                            currentPayerId);
                    if(chosenParticipants!=null) {
                        for (Payer p : chosenParticipants) {
                            expense.addPayer(p.getId(), Double.parseDouble(amount.getText().toString()) / chosenParticipants.size());//TODO: add amount in field
                        }
                    }
                        presenter.onCreateExpense(tripId, expense);
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
        Log.d("Set payerId ",Integer.toString(participants[position].getPersonId()));
        //if(view.getId() == R.id.spinner_paid_by){
            currentPayerId = participants[position].getPersonId();
        //}
    }

    public void onNothingSelected(AdapterView<?> parent) {
        parent.getItemAtPosition(0);
    }

    public void onSuccessAddingExpense(){
        Context context = getApplicationContext();
        CharSequence text = "Ausgabe hinzugefügt.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        presenter.onGetParticipantsForTrip(tripId);
        Intent intent = new Intent(this, ExpenseActivity.class);
        intent.putExtra("tripId", tripId);
        startActivity(intent);
        finish();
    }

    public void onViewParticipants(Participant[] _participants){

        participants = _participants;

        participantNames = new String[participants.length];
        for(int i = 0; i<participants.length;i++){
            participantNames[i] = participants[i].getUserName();
        }
        Spinner spinner = (Spinner)findViewById(R.id.spinner_paid_by);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, participantNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        currentPayerId = participants[0].getPersonId();
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

    public void updateParticipantList(){
        if(chosenParticipants == null){
            chosenParticipants=new ArrayList<Payer>();
        }
        chosenParticipants.clear();
        for(Integer i: chosenIds){
            chosenParticipants.add(new Payer(participants[i].getPersonId()));
        }
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.detach(fragment);
        fragment.refresh(chosenParticipants);
        fragmentTransaction.attach(fragment);
        fragmentTransaction.commit();
    }


    public void onOpenDialog(ArrayList<Integer> list){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final ArrayList<Integer> mSelectedItems = list;

        boolean[] checkedItems = new boolean[participantNames.length];
        for(int i = 0; i<checkedItems.length; i++){
            if(list.contains(i)){
                checkedItems[i] = true;
            }
            else{
                checkedItems[i] = false;
            }
        }
        // Set the dialog title
        builder.setTitle("Wer bezahlt mit?");
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
        builder.setMultiChoiceItems(participantNames, checkedItems,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which,
                                        boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            mSelectedItems.add(which);
                        } else if (mSelectedItems.contains(which)) {
                            // Else, if the item is already in the array, remove it
                            mSelectedItems.remove(which);
                        }
                    }
                });

        // Set the action buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog
                        chosenIds = mSelectedItems;
                        dialog.cancel();
                        updateParticipantList();
                    }
                })
                .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }


    @Override
    public void onClick(View v) {
        Log.d("onclick", String.valueOf(v.getId()));
        if(v.getId()== addButton.getId()){
            //open dialog to choose
            if(chosenIds!=null){
                onOpenDialog(chosenIds);
            }
            else{
                onOpenDialog(new ArrayList<Integer>());
            }
        }
    }


    public void onSuccessUpdateExpense(){
        Context context = getApplicationContext();
        CharSequence text = "Ausgabe geändert.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        Intent intent = new Intent(this, ExpenseActivity.class);
        intent.putExtra("tripId", tripId);
        startActivity(intent);
        finish();
    }

    public void setValues(Expense _expense){
        expense =_expense;

        title.setText(expense.getTitle());
        description.setText(expense.getDescription());
        amount.setText(String.valueOf(expense.getAmount()));
        currentPayerId = expense.getPayer();
        chosenParticipants = (ArrayList<Payer>) expense.getAssignedPayers();
        chosenIds=new ArrayList<Integer>();
        for(Payer p:chosenParticipants){
            chosenIds.add(p.getId());
        }
        progressDialog.cancel();
    }
}
