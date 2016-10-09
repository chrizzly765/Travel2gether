package de.traveltogether.invitation;

import de.traveltogether.model.Person;

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
    public void onGetFormerParticipants(long tripId) {
        interactor.getFormerParticipants(tripId, this);
    }

    @Override
    public void onError(String message){
        view.onViewError(message);
    }

    @Override
    public void onShowParticipants(Person[] persons) {
        view.onViewFormerParticipants(persons);
    }

    @Override
    public void onInvite(int personId, long tripId) {
        interactor.invite(tripId, personId, this);
    }

    @Override
    public void onInviteSuccess(){
        view.onInviteSuccess();
    }


}
