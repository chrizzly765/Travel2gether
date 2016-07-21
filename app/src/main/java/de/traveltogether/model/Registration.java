package de.traveltogether.model;

/**
 * Created by Anna-Lena on 27.05.2016.
 */
public class Registration {
    String name;
    String email;
    String password;
    String salt;
    String deviceId;

    public Registration(String _name, String _email, String _password, String _salt, String _deviceId){
        name = _name;
        email=_email;
        password=_password;
        salt=_salt;
        deviceId = _deviceId;
    }
}
