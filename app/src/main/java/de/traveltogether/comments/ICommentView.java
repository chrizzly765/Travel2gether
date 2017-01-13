package de.traveltogether.comments;

import de.traveltogether.model.Comment;

/**
 * Inferface for CommentView
 * Can either be a fragment or an activity
 */
public interface ICommentView {
    void onSuccessAddComment();
    void onViewComments(Comment[] comments);
}
