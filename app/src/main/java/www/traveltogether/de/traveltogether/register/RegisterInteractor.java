package www.traveltogether.de.traveltogether.register;

import www.traveltogether.de.traveltogether.model.Registration;
import www.traveltogether.de.traveltogether.servercommunication.JsonDecode;

/**
 * Created by Anna-Lena on 27.05.2016.
 */
public class RegisterInteractor implements IRegisterInteractor {
    @Override
    public void register(String name, String email, String password) {
        Registration reg = new Registration(name, email, password);

        String jsonString = JsonDecode.getInstance().classToJson(reg);


    }
}
