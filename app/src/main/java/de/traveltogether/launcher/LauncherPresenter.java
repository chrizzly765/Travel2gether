package de.traveltogether.launcher;

/**
 * Presenter for LauncherActivity
 * Implements ILauncherPresenter
 */
public class LauncherPresenter implements ILauncherPresenter {
    private LauncherActivity view;
    private ILauncherInteractor interactor;
    public LauncherPresenter(LauncherActivity activity) {
        view = activity;
        interactor = new LauncherInteractor();
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
