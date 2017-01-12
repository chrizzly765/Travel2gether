package de.traveltogether.notification;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import de.traveltogether.DataType;
import de.traveltogether.R;
import de.traveltogether.model.Notification;
import de.traveltogether.notification.NotificationAdapter;

/**
 * A fragment representing a list of Notifications.
 */
public class NotificationFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private NotificationAdapter adapter;
    private Notification[] notifications;
    private View view;
    private NotificationActivity activity;
    Notification currentNotification;

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
        if(!(notifications==null || notifications.length==0 )){
            adapter = new NotificationAdapter(getActivity(), notifications);
            setListAdapter(adapter);
            getListView().setOnItemClickListener(this);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Notification n = (Notification) adapter.getItem(position);
        ((NotificationActivity)getActivity()).currentNotification = n;
        activity.presenter.onSetNotificationRead(n.getId());
        view.setBackgroundResource(R.drawable.rectangle);
    }
}
