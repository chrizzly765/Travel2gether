package de.traveltogether.tasks.newtask;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.StaticTripData;
import de.traveltogether.datepicker.DatePickerFragment;
import de.traveltogether.model.Participant;
import de.traveltogether.model.Task;
import de.traveltogether.tasks.TaskListActivity;
import de.traveltogether.tasks.detail.TaskDetailActivity;

public class NewTaskActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener, View.OnClickListener {

    INewTaskPresenter presenter;
    TextView title;
    TextView description;
    TextView date;
    Spinner assignedTo;
    Spinner status;
    Task task;
    long tripId = -1;
    long featureId = -1;
    Participant[] participants;
    ArrayList<String> participantNames;
    int currentPersonId;
    ProgressDialog progressDialog;

    DatePickerFragment datePicker;
    ImageButton clickedDatePickerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.logo_ohne_schrift);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle(R.string.new_task);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            tripId = b.getLong("tripId", -1);
            featureId = b.getLong("featureId", -1);
        }

        title = (TextView) findViewById(R.id.activity_new_task_title);
        description = (TextView) findViewById(R.id.activity_new_task_description);
        date = (TextView) findViewById(R.id.activity_new_task_date);
        assignedTo = (Spinner) findViewById(R.id.spinner_assigned_to);
        status = (Spinner) findViewById(R.id.spinner_status);
        presenter = new NewTaskPresenter(this);
        datePicker =new DatePickerFragment();

        if(featureId != -1) {
            getSupportActionBar().setTitle("Aufgabe bearbeiten");
        }

        onViewParticipants(StaticTripData.getActiveParticipants());

        ImageButton datePickerBtn = (ImageButton) findViewById(R.id.button_datepicker_new_task);
        datePickerBtn.setOnClickListener(this);
        EditText datePickerText = (EditText) findViewById(R.id.activity_new_task_date);
        datePickerText.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(adapter);
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(featureId != -1) {
            presenter.onGetDetailsForTask(featureId);
        }
    }


    public void onClick(View v) {
        if (v.getId() == R.id.button_datepicker_new_task || v.getId() == R.id.activity_new_task_date){
            clickedDatePickerBtn = (ImageButton) v;
            datePicker.show(getFragmentManager(), DatePickerFragment.TAG);
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

                if(featureId == -1) {

                    Task task = new Task(
                            title.getText().toString(),
                            0,
                            tripId,
                            description.getText().toString(),
                            StaticData.getUserId(),
                            date.getText().toString(),
                            currentPersonId,
                            (int) status.getSelectedItemId() + 1
                    );

                    presenter.onCreateTask(task);
                }
                else if(featureId != -1) {

                    task.setTitle(title.getText().toString());
                    task.setDueDate(date.getText().toString());
                    task.setDescription(description.getText().toString());
                    task.setPersonId(currentPersonId);
                    task.setState((int) status.getSelectedItemId() + 1);
                    task.setLastUpdateBy(StaticData.getUserId());

                    presenter.onUpdateTask(task);
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
        if (position > 0) {
            // -1 because of the first entry "Bitte wählen"
            currentPersonId = participants[position-1].getPersonId();
        }
        else {
            currentPersonId = 0;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        parent.getItemAtPosition(0);
    }

    public void onSuccessAddingTask(){
        Toast.makeText(getApplicationContext(), R.string.task_added, Toast.LENGTH_SHORT).show();
        finish();
    }

    public void onSuccessUpdateTask(){
        tripId = task.getTripId();
        Toast.makeText(getApplicationContext(), R.string.task_updated, Toast.LENGTH_SHORT).show();
        finish();
    }

    public void onViewError(String message) {

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

    public void onViewParticipants(Participant[] _participants){

        participants = _participants;
        participantNames = new ArrayList<>();

        participantNames.add(getResources().getString(R.string.please_choose));
        for(int i = 0; i<participants.length;i++){
            participantNames.add(participants[i].getUserName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, participantNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignedTo.setAdapter(adapter);
        assignedTo.setOnItemSelectedListener(this);
        currentPersonId = participants[0].getPersonId();
    }

    public void onViewParticipants(Participant[] _participants, Task task){

        participants = _participants;
        participantNames = new ArrayList<>();
        int selectedPos = 0;

        participantNames.add(getResources().getString(R.string.please_choose));
        for(int i = 0; i<participants.length;i++){
            participantNames.add(participants[i].getUserName());
            if(participants[i].getPersonId() == task.getPersonId()) {
                currentPersonId = participants[i].getPersonId();

                // ++ because of first entry "Bitte wählen"
                selectedPos = i+1;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, participantNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignedTo.setAdapter(adapter);
        assignedTo.setOnItemSelectedListener(this);
        assignedTo.setSelection(selectedPos);
    }

    public void onViewDetails(Task _task) {

        task = _task;
        tripId=task.getTripId();
        title.setText(task.getTitle());
        description.setText(task.getDescription());
        date.setText(task.getDueDate());
        currentPersonId = task.getPersonId();
        status.setSelection(task.getState()-1);

        // fill dropdown with participants
        onViewParticipants(StaticTripData.getActiveParticipants(), task);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if(clickedDatePickerBtn.getId() == R.id.button_datepicker_new_task){
            int month = monthOfYear + 1;
            date.setText(dayOfMonth + "." + month+"." + year);
            datePicker.setDate(year, month, dayOfMonth+1);
        }
    }
}
