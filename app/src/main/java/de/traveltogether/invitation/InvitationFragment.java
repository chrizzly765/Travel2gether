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
import de.traveltogether.StaticData;
import de.traveltogether.model.Person;

import static android.content.Intent.createChooser;

/**
* Fragment holding a list of suggestions for participants
*/
public class InvitationFragment extends ListFragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Person[] formerParticipants;
    private InvitationAdapter adapter;
    private View view;
    private IInvitePresenter presenter;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        if(!(formerParticipants==null || formerParticipants.length==0 )){
            adapter = new InvitationAdapter(getActivity(), formerParticipants);
            setListAdapter(adapter);
            getListView().setOnItemClickListener(this);
            ViewGroup vg = getListView();
            int totalHeight = 0;
            for (int i = 0; i < adapter.getCount(); i++) {
                View listItem = adapter.getView(i, null, vg);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams par = getListView().getLayoutParams();
            par.height = totalHeight + (getListView().getDividerHeight() * (adapter.getCount() - 1));
            getListView().setLayoutParams(par);
            getListView().requestLayout();
        }

    }

    public void onResume(){
        super.onResume();
        view.findViewById(de.traveltogether.R.id.fragment_button_invite).setOnClickListener(this);
        view.findViewById(de.traveltogether.R.id.fragment_text_invite).setOnClickListener(this);
    }

    public void onClick(View v) {
        Intent invite = new Intent(Intent.ACTION_SEND);
        invite.putExtra(Intent.EXTRA_TEXT, getString(R.string.invitation_text) + "tripId=" + tripId + "&author=" + StaticData.getUserId());
        invite.setType("text/plain");
        v.setEnabled(false);
        v.setClickable(false);
        startActivity(createChooser(invite, getString(R.string.title_invititation_choose)));
        getActivity().finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Person person = (Person) adapter.getItem(position);
        int receiverId = person.getPersonId();
        presenter.onInvite(receiverId, tripId);
        view.setOnClickListener(null);
        view.findViewById(R.id.fragment_invitation_list_item_icon).setBackgroundResource(R.drawable.circledce641);
    }
}
