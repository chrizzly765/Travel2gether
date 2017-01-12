package de.traveltogether.register;

/**
 * Interface for presenter of RegisterActivity
 */
interface IRegisterPresenter {
     void onRegister(String name, String email, String password, String salt, String token);
     void onError(String message);
     void onSuccess(String message);
}
