package de.traveltogether.dialog.progressdialog;

import android.app.DialogFragment;
import android.widget.TextView;

import de.traveltogether.model.Task;

/**
 * Created by Isa on 27.11.2016.
 */

public class ProgressDialogFragment extends DialogFragment {
    private Task task;
    private long tripId;
    private TextView title;


    /*public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_progress_dialog, container, false);
        getDialog().setTitle("Fortschrittsanzeige");

        //title = (TextView) getActivity().findViewById(R.id.activity_task_detail_title);
        //title.setText(getTasksDone());
        return rootView;
    }*/

    public String getTasksDone (){
        String result = "";
        return result;
    }



    /*
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Fortschrittsanzeige");
        builder.setMessage("Erledigte Aufgaben");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        return builder.create();
    }
    */

    private class ProgressViewHolder {
        TextView name, amount;
    }
}

