package www.traveltogether.de.traveltogether.register;

import www.traveltogether.de.traveltogether.IInteractor;

/**
 * Created by Anna-Lena on 27.05.2016.
 */
public interface IRegisterInteractor extends IInteractor {
    public void register(String name, String email, String password, IRegisterPresenter listener);
}
