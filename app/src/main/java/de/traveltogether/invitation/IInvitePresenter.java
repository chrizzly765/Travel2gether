package de.traveltogether.invitation;

import de.traveltogether.model.Person;


public interface IInvitePresenter {
     void onGetFormerParticipants(long tripId);
     void onError(String message);
     void onShowParticipants(Person[] persons);
     void onInvite(int personId, long tripId);
     void onInviteSuccess();
}
