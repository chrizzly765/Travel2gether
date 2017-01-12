package de.traveltogether.login;

import de.traveltogether.IInteractor;

/**
 * Interface for Interactor of LoginActivity
 */
interface ILoginInteractor extends IInteractor{
    void login(String email, String hash, ILoginPresenter listener);
    void getSalt(String email, ILoginPresenter listener);
    void updateToken(String token, ILoginPresenter listener);
    void forgotPassword(String email, ILoginPresenter  listener);
}
