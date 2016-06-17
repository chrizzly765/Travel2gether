package www.traveltogether.de.traveltogether.invitation;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import www.traveltogether.de.traveltogether.R;

import static android.content.Intent.createChooser;

public class InvitationFragment extends Fragment implements View.OnClickListener {

    IInvitePresenter presenter;
    View view;

    public InvitationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new InvitePresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        super.onCreateView(inflater, container,savedInstanceState);
        view = inflater.inflate(R.layout.fragment_invitation, container, false);
        ImageButton button = (ImageButton)view.findViewById(R.id.fragment_button_invite);
        TextView text = (TextView)view.findViewById(R.id.fragment_text_invite);
        button.setOnClickListener(this);
        text.setOnClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void onClick(View v) {
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
}
