package de.traveltogether.comments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import de.traveltogether.R;
import de.traveltogether.model.Comment;

/**
 * A simple {@link android.app.Fragment} subclass.
 */
public class CommentFragment extends ListFragment implements View.OnClickListener {
    long id;
    ICommentPresenter presenter;
    Comment[] comments;
    EditText inputField;
    View activity;

    public CommentFragment() {
        // Required empty public constructor

    }


    public static CommentFragment newInstance(View _activity, long _id){
        CommentFragment fragment = new CommentFragment();
        fragment.activity = _activity;
        fragment.id = _id;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inputField = (EditText)activity.findViewById(R.id.fragment_comment_editText);
        Button send = (Button)activity.findViewById(R.id.fragment_comment_button_send);
        send.setOnClickListener(this);
        presenter = new CommentPresenter(this);
        presenter.onGetCommentsForFeature(id);
    }

    public void onViewComments(Comment[] _comments){
        comments = _comments;

        //Fragment in Activity einbetten
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CommentListFragment fragment = CommentListFragment.newInstance(comments);
        fragmentTransaction.add(R.id.fragment_comment_list_container, fragment);
        fragmentTransaction.commit();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.fragment_comment_button_send){
            String text = inputField.getText().toString();
            presenter.onSendComment(id, text);
        }
    }

    public void onSuccessAddComment(){
        presenter.onGetCommentsForFeature(id);
    }
}
