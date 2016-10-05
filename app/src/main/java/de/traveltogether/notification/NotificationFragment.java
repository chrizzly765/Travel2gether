package de.traveltogether.notification;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import de.traveltogether.DataType;
import de.traveltogether.R;
import de.traveltogether.chat.ChatActivity;
import de.traveltogether.info.InfoActivity;
import de.traveltogether.mainmenu.MainActivity;
import de.traveltogether.model.Notification;
import de.traveltogether.model.Trip;
import de.traveltogether.notification.NotificationAdapter;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class NotificationFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private NotificationAdapter adapter;
    private Notification[] notifications;
    View view;
    NotificationActivity activity;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NotificationFragment() {

    }

    public static NotificationFragment newInstance(Notification[] _notifications, NotificationActivity _activity ) {
        NotificationFragment fragment = new NotificationFragment();
        fragment.notifications = _notifications;
        fragment.activity = _activity;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (Notification n : notifications){
            Log.d("notification",n.getType() + " " + DataType.INVITATION.toString() + " " + n.getOpened());
            if(n.getType().equals(DataType.INVITATION.toString()) && n.getOpened()== false){
                InvitationDialogFragment.createDialog(activity, n);
                activity.presenter.onSetNotificationRead(n.getId());
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trip_list, container, false);

        return view;
    }

    public void onStart(){
        super.onStart();
        if(notifications==null || notifications.length==0 ){
            //do nothing
        }
        else {
            adapter = new NotificationAdapter(getActivity(), notifications);
            setListAdapter(adapter);
            getListView().setOnItemClickListener(this);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Notification n = (Notification) adapter.getItem(position);
        activity.presenter.onSetNotificationRead(n.getId());
        if (n.getType().equals(DataType.TRIP.toString())){
            Intent intent = new Intent(activity, InfoActivity.class);
            intent.putExtra("tripId", n.getFeatureOrTripId());
            startActivity(intent);
        }
        else if(n.getType().equals(DataType.EXPENSE.toString())){
            //Intent intent = new Intent(activity, ExpenseDetailActivity.class);
            //intent.putExtra("id", n.getFeatureOrTripId());
            //startActivity(intent);
        }
        else if (n.getType().equals(DataType.ACTIVITY.toString())){
            //Intent intent = new Intent(activity, ActivityDetailActivity.class);
            //intent.putExtra("id", n.getFeatureOrTripId());
            //startActivity(intent);
        }
        else if(n.getType().equals(DataType.TASK.toString())){
            //Intent intent = new Intent(activity, TaskDetailActivity.class);
            //intent.putExtra("id", n.getFeatureOrTripId());
            //startActivity(intent);
        }
        else if(n.getType().equals(DataType.CHAT.toString())){
            Intent intent = new Intent(activity, ChatActivity.class);
            intent.putExtra("tripId", n.getFeatureOrTripId());
            startActivity(intent);
        }
        else if(n.getType().equals(DataType.PACKINGOBJECT.toString())){
            //Intent intent = new Intent(activity, PackingDetailActivity.class);
            //intent.putExtra("id", n.getFeatureOrTripId());
            //startActivity(intent);
        }

    }
}