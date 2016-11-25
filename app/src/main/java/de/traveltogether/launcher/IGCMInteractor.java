package de.traveltogether.launcher;


import de.traveltogether.IInteractor;

/**
 * Created by Anna-Lena on 25.11.2016.
 */

public interface IGCMInteractor extends IInteractor {
    public void updateToken(String token, IGCMPresenter listener);
}
