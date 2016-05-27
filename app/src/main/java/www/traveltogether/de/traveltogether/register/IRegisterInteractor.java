package www.traveltogether.de.traveltogether.register;

/**
 * Created by Anna-Lena on 27.05.2016.
 */
public interface IRegisterInteractor {
    public void register(String name, String email, String password, IRegisterPresenter listener);
}
