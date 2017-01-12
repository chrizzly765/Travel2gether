package de.traveltogether.register;

import de.traveltogether.IInteractor;

/**
 * Interface for Interactor of RegisterActivity
 */
 interface IRegisterInteractor extends IInteractor {
     void register(String name, String email, String password, String salt, String token, IRegisterPresenter listener);
}
