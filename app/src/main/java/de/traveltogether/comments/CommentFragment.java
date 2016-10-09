package de.traveltogether.comments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.model.Comment;

/**
 * A simple {@link android.app.Fragment} subclass.
 */
public class CommentFragment extends Fragment implements View.OnClickListener, ICommentView {
    long id;
    ICommentPresenter presenter;
    Comment[] comments;
    EditText inputField;

    public CommentFragment() {
        // Required empty public constructor

    }


    public static CommentFragment newInstance(long _id){
        CommentFragment fragment = new CommentFragment();
        fragment.id = _id;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        inputField = (EditText)view.findViewById(R.id.fragment_comment_editText);
        ImageButton send = (ImageButton)view.findViewById(R.id.fragment_comment_button_send);
        send.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.fragment_comment_button_send){
            String text = inputField.getText().toString();
            presenter.onSendCommentForFeature(id, StaticData.getUserId(), text);

            //Text löschen wenn Eingabe fertig
            inputField.setText("");

            //Keyboard ausblenden wenn senden geklickt wurde
            InputMethodManager in = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(inputField.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void onSuccessAddComment(){
        presenter.onGetCommentsForFeature(id);
    }
}
