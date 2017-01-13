package de.traveltogether.dialog;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.util.Log;

import de.traveltogether.R;

/**
 * DialogFragment for DeleteDialog
 */
public class DeleteDialogFragment extends DialogFragment implements DialogInterface.OnClickListener{
    public static final String TAG = DeleteDialogFragment.class.getSimpleName();

    private DeleteActivity activity;

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
        activity.onActivityResult(getTargetRequestCode(), which, activity.getIntent());
        dialog.cancel();
    }
}

