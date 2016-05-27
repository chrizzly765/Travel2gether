package www.traveltogether.de.traveltogether.register;

import www.traveltogether.de.traveltogether.model.Activity;

/**
 * Created by Anna-Lena on 27.05.2016.
 */
public class RegisterPresenter implements IRegisterPresenter {
    private RegisterActivity registerActivity;
    private IRegisterInteractor registerInteractor;

    public RegisterPresenter(RegisterActivity _registerActivity){
        registerActivity = _registerActivity;
        registerInteractor = new RegisterInteractor();
    }

    @Override
    public void onRegister(String name, String email, String password) {
        registerInteractor.register(name, email, password);

    }
}
