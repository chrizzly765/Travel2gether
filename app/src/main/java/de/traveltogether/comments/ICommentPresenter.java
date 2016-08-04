package de.traveltogether.comments;

import de.traveltogether.model.Comment;

/**
 * Created by Anna-Lena on 15.06.2016.
 */
public interface ICommentPresenter {
    public void onGetCommentsForFeature(long id);
    public void onGetCommentsForTrip(long id);
    public void onSuccessCommentList(Comment[] comments);
    public void onSuccessAddComment();
    public void onSendComment(long id, String text);

}
