package www.traveltogether.de.traveltogether.comments;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import www.traveltogether.de.traveltogether.R;

/**
 * A simple {@link android.app.Fragment} subclass.
 */
public class CommentFragment extends ListFragment {
    long id;
    ICommentPresenter presenter;
    public CommentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CommentPresenter(this);
        presenter.onGetComments(id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }
}
