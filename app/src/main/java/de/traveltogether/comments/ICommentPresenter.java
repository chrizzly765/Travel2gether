package de.traveltogether.comments;

import de.traveltogether.model.Comment;

/**
 * Created by Anna-Lena on 15.06.2016.
 */
public interface ICommentPresenter {
    void onGetCommentsForFeature(long id);
    void onGetCommentsForTrip(long id);
    void onSuccessCommentList(Comment[] comments);
    void onSuccessAddComment();
    void onSendCommentForTrip(long id, int personId, String text);
    void onSendCommentForFeature(long id, int personId, String text);
}
