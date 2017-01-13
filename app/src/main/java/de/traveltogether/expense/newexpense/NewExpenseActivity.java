package de.traveltogether.expense.newexpense;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;
import java.util.Objects;

import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.StaticTripData;
import de.traveltogether.model.Expense;
import de.traveltogether.model.Participant;
import de.traveltogether.model.Payer;

/**
 * Activity for creating a new expense
 */
public class NewExpenseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{

    private INewExpensePresenter presenter;
    private EditText title;
    private EditText amount;
    private EditText description;
    private Spinner currencySpinner;
    private Spinner payedBySpinner;
    private int currentPayerId;
    private ImageButton addButton;
    private long tripId;
    private long featureId;
    private Participant[] participants;
    private String[] participantNames;
    private ArrayList<Integer> chosenIds = new ArrayList<Integer>();
    private ArrayList<Payer> chosenParticipants;
    private ProgressDialog progressDialog;
    private Switch shareEvenlySwitch;
    private ParticipantSelectionListFragment fragment;
    private Expense expense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            tripId = b.getLong("tripId", -1);
            featureId = b.getLong("featureId", -1);
        }

        presenter= new NewExpensePresenter(this);
        onViewParticipants(StaticTripData.getActiveParticipants());


        currencySpinner = (Spinner) findViewById(R.id.spinner_currency);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currencies, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(adapter);

        title= (EditText)findViewById(R.id.activity_new_expense_title);
        description= (EditText)findViewById(R.id.activity_new_expense_description);
        amount = (EditText)findViewById(R.id.activity_new_expense_amount);
        addButton = (ImageButton)findViewById(R.id.activity_new_expense_button_add);
        addButton.setOnClickListener(this);

        shareEvenlySwitch = (Switch)findViewById(R.id.activity_new_expense_switch);

        if(chosenParticipants == null){
            chosenParticipants = new ArrayList<Payer>();
        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment = ParticipantSelectionListFragment.newInstance(chosenParticipants);
        fragmentTransaction.add(R.id.activity_new_expense_listcontainer, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(featureId!=-1){
            getSupportActionBar().setTitle(getString(R.string.edit_expense));
            presenter.onGetDetailForExpense(featureId);
            progressDialog = ProgressDialog.show(this, "",
                    getString(R.string.please_wait), true);
        }
        else {
            getSupportActionBar().setTitle(getString(R.string.new_expense));
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_save:

                if(!StringEscapeUtils.escapeJava(title.getText().toString()).equals("")
                        && !StringEscapeUtils.escapeJava(amount.getText().toString()).equals("")){

                    progressDialog = ProgressDialog.show(this, "",
                            getString(R.string.please_wait), true);

                    // DEFAULT TEXT IF FIELDS ARE EMPTY
                    if(StringEscapeUtils.escapeJava(description.getText().toString()).equals("")){
                        description.setText("Keine Beschreibung");
                    }


                    if(chosenParticipants.size()<1){
                        chosenParticipants.add(new Payer(currentPayerId, Double.parseDouble(amount.getText().toString())));
                    }
                    if(featureId!=-1){
                        expense.setTitle(StringEscapeUtils.escapeJava(title.getText().toString()));
                        expense.setDescription(StringEscapeUtils.escapeJava(description.getText().toString()));
                        expense.setPayer(currentPayerId);
                        expense.setAmount(Double.parseDouble(amount.getText().toString()));
                        expense.setAssignedPayers(chosenParticipants);
                        expense.setLastUpdateBy(StaticData.getUserId());

                        for(Payer p : expense.getAssignedPayers()){
                            p.setAmount(Double.parseDouble(amount.getText().toString()) / expense.getAssignedPayers().size());
                        }

                        presenter.onUpdateExpense(expense);
                    }
                    else {
                        Expense expense = new Expense(
                                StringEscapeUtils.escapeJava(title.getText().toString()),
                                0,
                                tripId,
                                StringEscapeUtils.escapeJava(description.getText().toString()),
                                StaticData.getUserId(),
                                Double.parseDouble(amount.getText().toString()),
                                currentPayerId,
                                0);
                        if(chosenParticipants!=null) {
                            for (Payer p : chosenParticipants) {
                                expense.addPayer(p.getId(), Double.parseDouble(amount.getText().toString()) / chosenParticipants.size());//TODO: add amount in field
                            }
                        }
                        presenter.onCreateExpense(expense);
                    }
                    return true;
                }
                else{
                    onViewError(getString(R.string.mandatory_expense),getString(R.string.mandatory));
                    return false;
                }


            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
        currentPayerId = participants[position].getPersonId();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        parent.getItemAtPosition(0);
    }

    public void onSuccessAddingExpense(){
        Context context = getApplicationContext();
        CharSequence text = getString(R.string.added_expense);
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        progressDialog.cancel();
        finish();
    }

    public void onViewParticipants(Participant[] _participants){

        participants = _participants;

        participantNames = new String[participants.length];
        for(int i = 0; i<participants.length;i++){
            participantNames[i] = participants[i].getUserName();
        }
        payedBySpinner = (Spinner)findViewById(R.id.spinner_paid_by);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, participantNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        payedBySpinner.setAdapter(adapter);
        payedBySpinner.setOnItemSelectedListener(this);

        currentPayerId = participants[0].getPersonId();
    }

    public void onViewError(String message, String title) {
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
            checkedItems[i] = list.contains(i);
        }
        builder.setTitle(getString(R.string.title_expense));
        builder.setMultiChoiceItems(participantNames, checkedItems,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which,
                                        boolean isChecked) {
                        if (isChecked) {
                            mSelectedItems.add(which);
                        } else if (mSelectedItems.contains(which)) {
                            mSelectedItems.remove(which);
                        }
                    }
                });

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
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
        CharSequence text = getString(R.string.updated_expense);
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        progressDialog.cancel();
        finish();
    }

    public void setValues(Expense _expense){
        expense =_expense;
        tripId = expense.getTripId();
        title.setText(StringEscapeUtils.unescapeJava(expense.getTitle()));
        description.setText(StringEscapeUtils.unescapeJava(expense.getDescription()));
        amount.setText(String.valueOf(expense.getAmount()));
        currentPayerId = expense.getPayer();

        chosenParticipants = (ArrayList<Payer>) expense.getAssignedPayers();
        chosenIds=new ArrayList<Integer>();
        for(int i = 0; i<participants.length; i++){
            for(int j = 0; j<chosenParticipants.size(); j++){
                if(chosenParticipants.get(j).getId() == participants[i].getPersonId()) {
                    chosenIds.add(i);
                }
            }
        }
        int position = 0;
        for(int i = 0; i<participants.length; i++){
            if(participants[i].getPersonId() == currentPayerId){
                position = i;
            }
        }
        updateParticipantList();
        payedBySpinner.setSelection(position);
        progressDialog.cancel();
    }

}
