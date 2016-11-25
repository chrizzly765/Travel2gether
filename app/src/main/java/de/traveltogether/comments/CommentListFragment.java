package de.traveltogether.comments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.traveltogether.R;
import de.traveltogether.model.Comment;

/**
 * A fragment representing a list of Items.
 */
public class CommentListFragment extends ListFragment{

    Comment[] comments;
    CommentAdapter adapter;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CommentListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
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
        if(comments==null || comments.length==0 ){
            //TODO: show new trip listitem
        }
        else {
            adapter = new CommentAdapter(getActivity(), comments);
            setListAdapter(adapter);

            if (adapter == null) {
                return;
            }
            ViewGroup vg = getListView();
            int totalHeight = 0;
            for (int i = 0; i < adapter.getCount(); i++) {
                View listItem = adapter.getView(i, null, vg);
                listItem.measure(0, 0);
                totalHeight += 500;//(listItem.getMeasuredHeight());
            }

            ViewGroup.LayoutParams par = getListView().getLayoutParams();
            par.height = totalHeight + (getListView().getDividerHeight() * (adapter.getCount() - 1));
            getListView().setLayoutParams(par);
            getListView().requestLayout();
        }
    }

}
