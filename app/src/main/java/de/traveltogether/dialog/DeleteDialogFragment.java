package de.traveltogether.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.util.Log;

import de.traveltogether.R;
import de.traveltogether.model.Activity;


public class DeleteDialogFragment extends DialogFragment implements DialogInterface.OnClickListener{
    //public static final String TAG = InvitationDialogFragment.class.getSimpleName();
    public static final String TAG = DeleteDialogFragment.class.getSimpleName();

    DeleteActivity activity;

    public DeleteDialogFragment() {
        // Required empty public constructor
    }



    public static void createDialog(DeleteActivity _activity){
        DeleteDialogFragment dialog = new DeleteDialogFragment();
        dialog.activity = _activity;

        AlertDialog.Builder builder = new AlertDialog.Builder(dialog.activity);
        builder.setMessage(R.string.delete_message);
        builder.setTitle(R.string.delete);
        builder.setNegativeButton(dialog.activity.getString(R.string.no), dialog);
        builder.setPositiveButton(dialog.activity.getString(R.string.yes), dialog);
        AlertDialog ad = builder.create();
        ad.show();
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        Log.d("onclick", Integer.toString(which));

        activity.onActivityResult(getTargetRequestCode(), which, activity.getIntent());
        dialog.cancel();

    }
}

