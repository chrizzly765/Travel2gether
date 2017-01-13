package de.traveltogether.comments;

import android.app.ListFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.traveltogether.R;
import de.traveltogether.model.Comment;

/**
 * A fragment representing a list of comments.
 */
public class CommentListFragment extends ListFragment{

    private Comment[] comments;
    private CommentAdapter adapter;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CommentListFragment() {
    }

    public static CommentListFragment newInstance(Comment[] _comments) {
        CommentListFragment fragment = new CommentListFragment();
        fragment.comments = _comments;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commentlist_list, container, false);

        return view;
    }

    public void onStart(){
        super.onStart();
        if(!(comments==null || comments.length==0)){
            adapter = new CommentAdapter(getActivity(), comments);
            setListAdapter(adapter);

            if (adapter == null) {
                return;
            }
        }
    }

}
