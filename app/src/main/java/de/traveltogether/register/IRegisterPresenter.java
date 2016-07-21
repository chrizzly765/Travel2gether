package de.traveltogether.register;

/**
 * Created by Anna-Lena on 27.05.2016.
 */
public interface IRegisterPresenter {
    public void onRegister(String name, String email, String password, String salt, String token);
    public void onError(String message);
    public void onSuccess(String message);
}
