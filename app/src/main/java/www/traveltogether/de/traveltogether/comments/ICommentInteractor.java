package www.traveltogether.de.traveltogether.comments;

import www.traveltogether.de.traveltogether.IInteractor;

/**
 * Created by Anna-Lena on 15.06.2016.
 */
public interface ICommentInteractor extends IInteractor {
    void getComments(long id, CommentPresenter commentPresenter);
}
