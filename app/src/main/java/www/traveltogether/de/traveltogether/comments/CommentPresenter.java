package www.traveltogether.de.traveltogether.comments;

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
    public void onGetComments(long id) {
        interactor.getComments(id, this);

    }
}
