package de.traveltogether.comments;

import de.traveltogether.model.Comment;

/**
 * Created by Anna-Lena on 04.08.2016.
 */
public interface ICommentView {
    void onSuccessAddComment();
    void onViewComments(Comment[] comments);
}
