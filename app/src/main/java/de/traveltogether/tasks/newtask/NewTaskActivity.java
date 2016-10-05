package de.traveltogether.tasks.newtask;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.model.Participant;
import de.traveltogether.model.Task;

public class NewTaskActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    INewTaskPresenter presenter;
    TextView title;
    TextView description;
    TextView date;
    Spinner assignedTo;
    Spinner status;
    Task task;
    long tripId;
    long featureId;
    Participant[] participants;
    String[] participantNames;
    int currentPersonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        getSupportActionBar().setTitle("Neue Aufgabe");
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

        if(featureId != -1) {
            presenter.onGetDetailsForTask(featureId);
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(adapter);

        onViewParticipants(StaticData.getActiveParticipants());
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

                    presenter.onCreateTask(tripId, task);
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
        if(view.getId() == R.id.spinner_assigned_to){
            currentPersonId = participants[((Spinner)view).getSelectedItemPosition()].getPersonId();
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        parent.getItemAtPosition(0);
    }

    public void onSuccessAddingTask(){
        Context context = getApplicationContext();
        CharSequence text = "Aufgabe hinzugefügt.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        finish();
    }

    public void onSuccessUpdateTask(){
        Context context = getApplicationContext();
        CharSequence text = "Aufgabe aktualisiert.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        finish();
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


    public void onViewParticipants(Participant[] _participants){

        participants = _participants;

        participantNames = new String[participants.length];
        for(int i = 0; i<participants.length;i++){
            participantNames[i] = participants[i].getUserName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, participantNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignedTo.setAdapter(adapter);
        assignedTo.setOnItemSelectedListener(this);

        currentPersonId = participants[0].getPersonId();
    }

    public void onViewDetails(Task _task) {

        task = _task;
        title.setText(task.getTitle());
        description.setText(task.getDescription());
        date.setText(task.getDueDate());
        currentPersonId = task.getPersonId();
        status.setSelection(task.getState()-1);
    }
}
