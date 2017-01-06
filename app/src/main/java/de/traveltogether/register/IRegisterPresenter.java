package de.traveltogether.register;

/**
 * Created by Anna-Lena on 27.05.2016.
 */
interface IRegisterPresenter {
     void onRegister(String name, String email, String password, String salt, String token);
     void onError(String message);
     void onSuccess(String message);
}
