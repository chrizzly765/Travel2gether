package de.traveltogether.invitation;

import de.traveltogether.IInteractor;

/**
 * Created by Anna-Lena on 16.05.2016.
 */
public interface IInviteInteractor extends IInteractor {
    public void getFormerParticipants(long tripId, IInvitePresenter _presenter);
    public void invite(long tripId, int personId, IInvitePresenter listener);
}
