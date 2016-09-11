package de.traveltogether.invitation;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import de.traveltogether.R;
import de.traveltogether.model.Person;

import static android.content.Intent.createChooser;

/**
*Fragment, dass die Liste der ehemaligen Mitreisenden anzeigt
*/
public class InvitationFragment extends ListFragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    Person[] formerParticipants;
    InvitationAdapter adapter;
    View view;
    IInvitePresenter presenter;
    long tripId;

    public InvitationFragment() {
        // Required empty public constructor
    }

    public static InvitationFragment newInstance(Person[] persons, IInvitePresenter _presenter, long _tripId) {
        InvitationFragment fragment = new InvitationFragment();
        fragment.formerParticipants = persons;
        fragment.presenter=_presenter;
        fragment.tripId = _tripId;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container,savedInstanceState);

        view = inflater.inflate(de.traveltogether.R.layout.fragment_invitation, container, false);
        ImageButton button = (ImageButton)view.findViewById(de.traveltogether.R.id.fragment_button_invite);
        TextView text = (TextView)view.findViewById(de.traveltogether.R.id.fragment_text_invite);
        button.setOnClickListener(this);
        text.setOnClickListener(this);
        return view;
    }

    public void onStart(){
        super.onStart();
        if(formerParticipants==null || formerParticipants.length==0 ){
            //TODO: show empty participant list?
        }
        else {
            adapter = new InvitationAdapter(getActivity(), formerParticipants);
            setListAdapter(adapter);
            getListView().setOnItemClickListener(this);
        }
    }

    public void onClick(View v) {
        //Chooser: auswählen womit der Link geöffnet werden soll
        if(v.getId() == R.id.fragment_button_invite) {
            Intent invite = new Intent(Intent.ACTION_SEND);
            invite.putExtra(Intent.EXTRA_TEXT, getString(R.string.invitation_text));
            invite.setType("text/plain");
            startActivity(createChooser(invite, getString(R.string.title_invititation_choose)));
        }
         else if (v.getId() == R.id.fragment_text_invite) {
            Intent invite = new Intent(Intent.ACTION_SEND);
            invite.putExtra(Intent.EXTRA_TEXT, getString(R.string.invitation_text));
            invite.setType("text/plain");
            startActivity(createChooser(invite, getString(R.string.title_invititation_choose)));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //bei click auf eine Person
        Person person = (Person) adapter.getItem(position);
        int receiverId = person.getPersonId();
        presenter.onInvite(receiverId, tripId);
    }
}
