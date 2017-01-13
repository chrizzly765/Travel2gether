package de.traveltogether.dialog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Abstract Activity
 * If DeleteActivity is extended, delete has to be implemented
 */

public abstract class DeleteActivity extends AppCompatActivity {
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == -1){
            delete();
        }
        /*if(resultCode == -2){
            //nothing happens
        }*/
    }

    protected void createDeleteDialog(){
        DeleteDialogFragment.createDialog(this);
    }

    protected abstract void delete();
}
