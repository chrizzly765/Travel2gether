package de.traveltogether.login;

import de.traveltogether.IInteractor;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public interface ILoginInteractor extends IInteractor{

    public void login(String email, String hash, String _token, ILoginPresenter listener);
    public void getSalt(String email, ILoginPresenter listener);
}
