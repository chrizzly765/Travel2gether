package de.traveltogether.invitation;

import de.traveltogether.model.Person;

/**
 * Created by Anna-Lena on 16.05.2016.
 */
public interface IInvitePresenter {
    public void onGetFormerParticipants(long tripId);
    public void onError(String message);
    public void onShowParticipants(Person[] persons);
    public void onInvite(int personId, long tripId);
    public void onInviteSuccess();
}
