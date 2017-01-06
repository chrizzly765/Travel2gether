package de.traveltogether.launcher;


import de.traveltogether.IInteractor;



 interface IGCMInteractor extends IInteractor {
     void updateToken(String token, IGCMPresenter listener);
     void sendInvitation(long tripId, int author, IGCMPresenter listener);
}
