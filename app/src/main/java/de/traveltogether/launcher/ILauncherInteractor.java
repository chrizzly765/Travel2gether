package de.traveltogether.launcher;


import de.traveltogether.IInteractor;

/**
 * Interface for interactor of LauncherActivity
 */
 interface ILauncherInteractor extends IInteractor {
     void updateToken(String token, ILauncherPresenter listener);
     void sendInvitation(long tripId, int author, ILauncherPresenter listener);
}
