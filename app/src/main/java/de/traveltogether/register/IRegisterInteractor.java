package de.traveltogether.register;

import de.traveltogether.IInteractor;

/**
 * Created by Anna-Lena on 27.05.2016.
 */
public interface IRegisterInteractor extends IInteractor {
    public void register(String name, String email, String password, String salt, String token, IRegisterPresenter listener);
}
