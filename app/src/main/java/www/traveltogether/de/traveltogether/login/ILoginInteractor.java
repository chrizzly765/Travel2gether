package www.traveltogether.de.traveltogether.login;

import www.traveltogether.de.traveltogether.IInteractor;
import www.traveltogether.de.traveltogether.register.IRegisterPresenter;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public interface ILoginInteractor extends IInteractor{

    public void login(String email, String hash, ILoginPresenter listener);
}
