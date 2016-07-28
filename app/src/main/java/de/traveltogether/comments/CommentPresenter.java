package de.traveltogether.comments;

import de.traveltogether.model.Comment;

/**
 * Created by Anna-Lena on 15.06.2016.
 */
public class CommentPresenter implements ICommentPresenter {
    ICommentInteractor interactor;
    CommentFragment view;
    public CommentPresenter(CommentFragment commentFragment){
        view = commentFragment;
        interactor = new CommentInteractor();
    }
    @Override
    public void onGetCommentsForFeature(long id) {
        interactor.getCommentsForFeature(id, this);

    }

    public void onGetCommentsForTrip(long id){
        interactor.getCommentsForTrip(id, this);
    }

    public void onSuccess(Comment[] comments){
        view.onViewComments(comments);
    }
}