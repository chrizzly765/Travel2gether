package de.traveltogether.model;


public class Registration {
    private String name;
    private String email;
    private String password;
    private String salt;
    private String deviceId;

    public Registration(String _name, String _email, String _password, String _salt, String _deviceId){
        name = _name;
        email=_email;
        password=_password;
        salt=_salt;
        deviceId = _deviceId;
    }
}
