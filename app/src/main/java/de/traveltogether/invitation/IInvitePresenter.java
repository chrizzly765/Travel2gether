package de.traveltogether.invitation;

import de.traveltogether.model.Person;

/**
 * Created by Anna-Lena on 16.05.2016.
 */
public interface IInvitePresenter {
    public void onGetFormerParticipants();
    public void onError(String message);
    public void onSuccess(Person[] persons);
}
