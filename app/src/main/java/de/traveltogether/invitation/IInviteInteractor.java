package de.traveltogether.invitation;

import de.traveltogether.IInteractor;

/**
 * Interface for Interactor of InvitationActivity
 */
 interface IInviteInteractor extends IInteractor {
     void getFormerParticipants(long tripId, IInvitePresenter _presenter);
     void invite(long tripId, int personId, IInvitePresenter listener);
}
