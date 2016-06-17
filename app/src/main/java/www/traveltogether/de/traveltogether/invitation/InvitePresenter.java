package www.traveltogether.de.traveltogether.invitation;

/**
 * Created by Anna-Lena on 16.05.2016.
 */
public class InvitePresenter implements IInvitePresenter {
    InvitationFragment view;
    IInviteInteractor interactor;
    public InvitePresenter(InvitationFragment _view) {
        view = _view;
        interactor = new InviteInteractor();
    }
}
