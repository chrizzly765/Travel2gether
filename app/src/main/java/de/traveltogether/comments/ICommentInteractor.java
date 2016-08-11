package de.traveltogether.comments;

import de.traveltogether.IInteractor;

/**
 * Created by Anna-Lena on 15.06.2016.
 */
public interface ICommentInteractor extends IInteractor {
    void getCommentsForFeature(long id, ICommentPresenter commentPresenter);
    void getCommentsForTrip(long id, ICommentPresenter commentPresenter);
    void sendCommentForFeature(long id, int personId, String text, ICommentPresenter listener);
    void sendCommentForTrip(long id,int personId, String text, ICommentPresenter listener);
}
