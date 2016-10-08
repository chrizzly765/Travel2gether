package de.traveltogether.packinglist.newpackingitem;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;





import de.traveltogether.StaticData;
import de.traveltogether.R;
import de.traveltogether.model.PackingItem;
import de.traveltogether.model.PackingObject;
import de.traveltogether.model.Participant;

public class NewPackingItemActivity extends AppCompatActivity implements View.OnClickListener {

    INewPackingItemPresenter presenter;
    EditText title;
    EditText number;
    EditText description;
    long tripId = -1;
    long featureId =-1;
    Participant[] participants;
    String[] participantNames;
    ArrayList<Integer> chosenIds = new ArrayList<Integer>();
    ArrayList<PackingItem> chosenParticipants;
    PackingObject packingObject;
    ImageButton addButton;
    PackingItemSelectionFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_packing_item);
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
        participants = StaticData.getActiveParticipants();
        participantNames = new String[StaticData.getActiveParticipants().length];
        for(int i = 0; i < StaticData.getActiveParticipants().length; i++){
            participantNames[i] = StaticData.getActiveParticipants()[i].getUserName();
        }

        presenter = new NewPackingItemPresenter(this);
        if(featureId!=-1){
            getSupportActionBar().setTitle("Packelement bearbeiten");
            presenter.onGetDetailForPackingObject(featureId);
            //progressDialog = ProgressDialog.show(this, "",
                    //"Bitte warten...", true);
        }
        else {
            getSupportActionBar().setTitle("Neues Packelement");
        }
        if(chosenParticipants == null){
            chosenParticipants = new ArrayList<PackingItem>();
        }


        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment = PackingItemSelectionFragment.newInstance(chosenParticipants);
        fragmentTransaction.add(R.id.activity_new_packingobject_listcontainer, fragment);
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
                if(title.getText().toString()=="" || number.getText().toString()=="") {
                    onViewError("Bitte gib die Daten vollständig an");
                    return false;
                }
                if(chosenParticipants.size() > Integer.parseInt(number.getText().toString())){
                    number.setText(String.valueOf(chosenParticipants.size()));
                }
                if(featureId!=-1){
                    PackingObject packingobject = new PackingObject(title.getText().toString(),
                            this.packingObject.getId(),
                            tripId, description.getText().toString(),
                            this.packingObject.getAuthor(),
                            Integer.parseInt(number.getText().toString()));

                    if(chosenParticipants!=null) {
                        /*for (PackingItem p : chosenParticipants) {
                            packingobject.addPackingItem(p);//TODO: add amount in field
                        }*/
                        PackingItem[] array= new PackingItem[chosenParticipants.size()];
                        for (int i=0; i<chosenParticipants.size();i++) {
                            PackingItem it = new PackingItem((int)chosenParticipants.get(i).getId());
                        }
                        packingobject.setPackingItems(array);
                    }
                    presenter.onUpdatePackingObject(packingobject);
                }
                else {
                    PackingObject packingobject = new PackingObject(
                            title.getText().toString(),
                            0,
                            tripId,
                            description.getText().toString(),
                            StaticData.getUserId(),
                            Integer.parseInt(number.getText().toString())
                    );
                    if(chosenParticipants!=null) {
                        PackingItem[] array= new PackingItem[chosenParticipants.size()];
                        for (int i=0; i<chosenParticipants.size();i++) {
                            PackingItem it = new PackingItem((int)chosenParticipants.get(i).getId());
                        }
                        packingobject.setPackingItems(array);

                    }
                    presenter.onCreatePackingObject(packingobject);

                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    public void onSuccessGetDetail(PackingObject _packingObject){
        packingObject = _packingObject;
        title.setText(packingObject.getTitle());
        number.setText(packingObject.getPackingItemsNumber());
        description.setText(packingObject.getDescription());
        for(PackingItem p: packingObject.getItems()){
            chosenParticipants.add(p);
        }
        //chosenParticipants = (List<PackingItem>)packingObject.getItems();
        chosenIds = new ArrayList<Integer>();
        for(PackingItem p:chosenParticipants){
            chosenIds.add((int)p.getId());
        }
    }

    public void onSuccessUpdatePackingObject(){
        Context context = getApplicationContext();
        CharSequence text = "Erfolgreich geändert.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        finish();
    }

    public void onSuccessAddingPackingObject(){
        Context context = getApplicationContext();
        CharSequence text = "Erfolgreich hinzugefügt.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
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
            if(list.contains(i)){
                checkedItems[i] = true;
            }
            else{
                checkedItems[i] = false;
            }
        }
        // Set the dialog title
        builder.setTitle("Wer packt ein?");
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
