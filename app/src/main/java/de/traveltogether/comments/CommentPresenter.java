package de.traveltogether.comments;

import de.traveltogether.model.Comment;

/**
 * Presenter for ICommentView
 * Implements ICommentPresenter
 */
public class CommentPresenter implements ICommentPresenter {
    private ICommentInteractor interactor;
    private ICommentView view;
    public CommentPresenter(ICommentView commentFragment){
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

    public void onSuccessCommentList(Comment[] comments){
        view.onViewComments(comments);
    }

    @Override
    public void onSuccessAddComment() {
        view.onSuccessAddComment();
    }

    @Override
    public void onSendCommentForTrip(long id, int personId, String text) {
        interactor.sendCommentForTrip(id,personId,text,this);
    }

    @Override
    public void onSendCommentForFeature(long id, int personId, String text) {
        interactor.sendCommentForFeature(id,personId,text, this);
    }
}
