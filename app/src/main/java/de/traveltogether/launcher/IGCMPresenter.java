package de.traveltogether.launcher;

/**
 * Created by Anna-Lena on 25.11.2016.
 */

interface IGCMPresenter {
     void onUpdateToken(String token);
     void onSendInvitation(long tripId, int author);
}
