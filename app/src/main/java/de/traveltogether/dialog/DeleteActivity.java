package de.traveltogether.dialog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Maria Dreher on 26.11.2016.
 */

public abstract class DeleteActivity extends AppCompatActivity {
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == -1){
            delete();
        }
        /*if(resultCode == -2){

        }*/
    }

    protected void createDeleteDialog(){
        DeleteDialogFragment.createDialog(this);
    }

    protected abstract void delete();
}
