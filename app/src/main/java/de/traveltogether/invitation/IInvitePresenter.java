package de.traveltogether.invitation;

import de.traveltogether.model.Person;

/**
 * Interface for presenter of InvitationActivity
 */
public interface IInvitePresenter {
     void onGetFormerParticipants(long tripId);
     void onError(String message);
     void onShowParticipants(Person[] persons);
     void onInvite(int personId, long tripId);
     void onInviteSuccess();
}
