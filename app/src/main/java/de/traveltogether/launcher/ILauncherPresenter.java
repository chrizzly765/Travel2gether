package de.traveltogether.launcher;

/**
 * Interface for Presenter of LauncherActivity
 */
interface ILauncherPresenter {
     void onUpdateToken(String token);
     void onSendInvitation(long tripId, int author);
}
