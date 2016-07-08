package www.traveltogether.de.traveltogether.invitation;

import www.traveltogether.de.traveltogether.model.Person;

/**
 * Created by Anna-Lena on 16.05.2016.
 */
public class InvitePresenter implements IInvitePresenter {
    InvitationActivity view;
    IInviteInteractor interactor;

    public InvitePresenter(InvitationActivity _view) {
        view = _view;
        interactor = new InviteInteractor();
    }

    @Override
    public void onGetFormerParticipants() {
        interactor.getFormerParticipants(this);
    }

    @Override
    public void onError(String message){
        view.onViewError(message);
    }

    @Override
    public void onSuccess(Person[] persons) {
        view.onViewFormerParticipants(persons);
    }
}
