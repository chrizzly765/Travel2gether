package de.traveltogether.packinglist.newpackingitem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.app.AlertDialog;
import android.content.DialogInterface;

import java.util.ArrayList;





import de.traveltogether.StaticData;
import de.traveltogether.R;
import de.traveltogether.model.PackingItem;
import de.traveltogether.model.PackingObject;
import de.traveltogether.model.Participant;

public class NewPackingItemActivity extends AppCompatActivity {

    INewPackingItemPresenter presenter;
    EditText title;
    EditText number;
    EditText description;
    long tripId;
    long featureId;
    Participant[] participants;
    String[] participantNames;
    ArrayList<Integer> chosenIds = new ArrayList<Integer>();
    ArrayList<PackingItem> chosenParticipants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_packing_item);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_save:
                if(title.getText().toString()=="" || number.getText().toString()==""){
                    onViewError("Bitte gib die Daten vollständig an");
                }

                PackingObject packingobject = new PackingObject(
                        title.getText().toString(),
                        0,
                        description.getText().toString(),
                        StaticData.getUserId(),
                        Double.parseDouble(number.getText().toString()),
                        /*currentPayerId*/);
                if(chosenParticipants!=null) {
                    for (PackingItem p : chosenParticipants) {
                        packingobject.addPackingItem(/*p.getId(), p.getAmount()*/);//TODO: add amount in field
                    }
                }
                if(featureId!=-1){
                    presenter.onUpdatePackingObject(packingobject);
                }
                else {
                    presenter.onCreatePackingObject(tripId, packingobject);
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
}
