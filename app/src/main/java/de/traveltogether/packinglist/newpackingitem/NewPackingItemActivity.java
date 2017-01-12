package de.traveltogether.packinglist.newpackingitem;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.ImageButton;
import android.widget.Toast;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;
import java.util.Objects;


import de.traveltogether.StaticData;
import de.traveltogether.R;
import de.traveltogether.StaticTripData;
import de.traveltogether.model.PackingItem;
import de.traveltogether.model.PackingObject;
import de.traveltogether.model.Participant;

/**
 * Activity for creating a new packing item
 */
public class NewPackingItemActivity extends AppCompatActivity implements View.OnClickListener {

    private INewPackingItemPresenter presenter;
    private EditText title;
    private EditText number;
    private EditText description;
    private long tripId = -1;
    private long featureId =-1;
    private Participant[] participants;
    private String[] participantNames;
    private ArrayList<Integer> chosenIds = new ArrayList<Integer>();
    private ArrayList<PackingItem> chosenParticipants;
    private PackingObject packingObject;
    private ImageButton addButton;
    private PackingItemSelectionFragment fragment;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_packing_item);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            tripId = b.getLong("tripId", -1);
            featureId = b.getLong("featureId", -1);
        }

        title = (EditText) findViewById(R.id.activity_new_packingobject_title);
        number =(EditText) findViewById(R.id.activity_new_packingobject_count);
        description = (EditText) findViewById(R.id.activity_new_packingobject_description);
        addButton = (ImageButton)findViewById(R.id.activity_new_packingobject_button_add);
        addButton.setOnClickListener(this);
        participants = StaticTripData.getActiveParticipants();
        participantNames = new String[StaticTripData.getActiveParticipants().length];
        for(int i = 0; i < StaticTripData.getActiveParticipants().length; i++){
            participantNames[i] = StaticTripData.getActiveParticipants()[i].getUserName();
        }
        presenter = new NewPackingItemPresenter(this);
        if(chosenParticipants == null){
            chosenParticipants = new ArrayList<PackingItem>();
        }
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment = PackingItemSelectionFragment.newInstance(chosenParticipants);
        fragmentTransaction.add(R.id.activity_new_packingobject_listcontainer, fragment);
        fragmentTransaction.commit();
    }


    protected void onStart(){
        super.onStart();
        if(featureId!=-1){
            getSupportActionBar().setTitle(getString(R.string.edit_packingitem));
            presenter.onGetDetailForPackingObject(featureId);
            progressDialog = ProgressDialog.show(this, "",
                    getString(R.string.packingitem) + getString(R.string.is_loading), true);
        }
        else {
            getSupportActionBar().setTitle("Neues Packelement");
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
                if(!StringEscapeUtils.escapeJava(title.getText().toString()).equals("")){
                    progressDialog = ProgressDialog.show(this, "",
                            getString(R.string.please_wait), true);

                    // DEFAULT TEXT IF FIELDS ARE EMPTY
                    if(StringEscapeUtils.escapeJava(description.getText().toString()).equals("")){
                        description.setText("Keine Beschreibung");
                    }

                    if(StringEscapeUtils.escapeJava(number.getText().toString()).equals("")){
                        number.setText("1");
                    }
                    if(chosenParticipants.size() > Integer.parseInt(number.getText().toString())){
                        number.setText(String.valueOf(chosenParticipants.size()));
                    }
                    if(featureId!=-1){
                        PackingObject packingobject = new PackingObject(StringEscapeUtils.escapeJava(title.getText().toString()),
                                this.packingObject.getId(),
                                tripId, StringEscapeUtils.escapeJava(description.getText().toString()),
                                this.packingObject.getAuthor(),
                                Integer.parseInt(number.getText().toString()));

                        if(chosenParticipants!=null) {
                            for (PackingItem p : chosenParticipants) {
                                packingobject.addPackingItem(p);
                            }
                        }
                        presenter.onUpdatePackingObject(packingobject);
                    }
                    else {
                        PackingObject packingobject = new PackingObject(
                                StringEscapeUtils.escapeJava(title.getText().toString()),
                                0,
                                tripId,
                                StringEscapeUtils.escapeJava(description.getText().toString()),
                                StaticData.getUserId(),
                                Integer.parseInt(number.getText().toString())
                        );
                        if(chosenParticipants!=null) {
                            PackingItem[] array= new PackingItem[chosenParticipants.size()];
                            for (PackingItem p : chosenParticipants) {
                                packingobject.addPackingItem(p);
                            }
                        }
                        presenter.onCreatePackingObject(packingobject);

                    }
                    return true;
                }
                else{
                    onViewError(getString(R.string.missing_title_male, getString(R.string.packingitem)),getString(R.string.mandatory));
                    return false;
                }


            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    public void onSuccessGetDetail(PackingObject _packingObject){
        packingObject = _packingObject;
        tripId=packingObject.getTripId();
        title.setText(StringEscapeUtils.unescapeJava(packingObject.getTitle()));
        number.setText(Integer.toString(packingObject.getPackingItemsNumber()));
        description.setText(StringEscapeUtils.unescapeJava(packingObject.getDescription()));
        for(PackingItem p: packingObject.getItems()){
            chosenParticipants.add(p);
        }
        chosenIds = new ArrayList<Integer>();
        for(PackingItem p:chosenParticipants){
            for(int i = 0; i< participants.length; i++) {
                if(p.getAssignedPerson() == participants[i].getPersonId())
                    chosenIds.add(i);
            }
        }
        updateParticipantList();
        progressDialog.cancel();
    }

    public void onSuccessUpdatePackingObject(){
        Context context = getApplicationContext();
        CharSequence text = getString(R.string.success_change);
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        progressDialog.cancel();
        finish();
    }

    public void onSuccessAddingPackingObject(){
        Context context = getApplicationContext();
        CharSequence text = getString(R.string.add_packingitem_success);
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        progressDialog.cancel();
        finish();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == addButton.getId()){
            if(chosenIds != null){
                onOpenDialog(chosenIds);
            }
            else{
                onOpenDialog(new ArrayList<Integer>());
            }
        }
    }

    public void onOpenDialog(ArrayList<Integer> list){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final ArrayList<Integer> mSelectedItems = list;

        boolean[] checkedItems = new boolean[participantNames.length];

        for(int i = 0; i<checkedItems.length; i++){
            checkedItems[i] = list.contains(i);
        }
        builder.setTitle(getString(R.string.who_packs));

        builder.setMultiChoiceItems(participantNames, checkedItems,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which,
                                        boolean isChecked) {
                        if (isChecked) {
                            if(!mSelectedItems.contains(which)) {
                                mSelectedItems.add(which);
                            }
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
                chosenIds = mSelectedItems;
                dialog.cancel();
                updateParticipantList();
            }
        })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void updateParticipantList(){
        if(chosenParticipants == null){
            chosenParticipants=new ArrayList<PackingItem>();
        }
        chosenParticipants.clear();
        for(Integer i: chosenIds){
            chosenParticipants.add(new PackingItem(participants[i].getPersonId()));
        }
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.detach(fragment);
        fragment.refresh(chosenParticipants);
        fragmentTransaction.attach(fragment);
        fragmentTransaction.commit();
    }
}
