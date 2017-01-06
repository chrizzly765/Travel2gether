package de.traveltogether.chat;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;

import org.apache.commons.lang3.StringEscapeUtils;

import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.comments.CommentListFragment;
import de.traveltogether.comments.CommentPresenter;
import de.traveltogether.comments.ICommentView;
import de.traveltogether.comments.ICommentPresenter;
import de.traveltogether.model.Comment;

/**
 * A simple {@link android.app.Fragment} subclass.
 */
public class ChatFragment extends Fragment implements View.OnClickListener, ICommentView {
    private long id;
    private ICommentPresenter presenter;
    private Comment[] comments;
    private EditText inputField;
    private ProgressDialog progressDialog;
    private CommentListFragment chatFragment;
    private View view;

    public ChatFragment () {
        // Required empty public constructor

    }


    public static ChatFragment newInstance(long _id){
        ChatFragment fragment = new ChatFragment();
        fragment.id = _id;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new CommentPresenter(this);
        presenter.onGetCommentsForTrip(id);
        progressDialog = ProgressDialog.show(getActivity(), "",
                "Nachrichten werden geladen...", true);
    }

    public void onRefresh(){
        presenter.onGetCommentsForTrip(id);
    }

    public void onViewComments(Comment[] _comments){
        progressDialog.cancel();

        comments = _comments;

        //Fragment in Activity einbetten
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(chatFragment!=null) {
            fragmentTransaction.remove(chatFragment);
        }
        chatFragment = CommentListFragment.newInstance(comments);
        fragmentTransaction.add(R.id.fragment_comment_list_container, chatFragment);
        fragmentTransaction.commit();

        ScrollView scrollView = (ScrollView)(view.findViewById(R.id.fragment_comment_scrollview));
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_comment, container, false);
        inputField = (EditText)view.findViewById(R.id.fragment_comment_editText);
        ImageButton send = (ImageButton)view.findViewById(R.id.fragment_comment_button_send);
        send.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.fragment_comment_button_send){
            String text = StringEscapeUtils.escapeJava(inputField.getText().toString());
            presenter.onSendCommentForTrip(id, StaticData.getUserId(), text);

            //Text löschen wenn Eingabe fertig
            inputField.setText("");

            //Keyboard ausblenden wenn senden geklickt wurde
            InputMethodManager in = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(inputField.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void onSuccessAddComment(){
        presenter.onGetCommentsForTrip(id);
    }
}
