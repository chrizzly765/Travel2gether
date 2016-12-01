package de.traveltogether.launcher;

/**
 * Created by Anna-Lena on 25.11.2016.
 */

public class GCMPresenter implements IGCMPresenter {
    LauncherActivity view;
    IGCMInteractor interactor;
    public GCMPresenter (LauncherActivity activity) {
        view = activity;
        interactor = new GCMInteractor();
    }

    @Override
    public void onUpdateToken(String token) {
        interactor.updateToken(token, this);
    }

    @Override
    public void onSendInvitation(long tripId, int author) {
        interactor.sendInvitation(tripId, author, this);
    }
}
