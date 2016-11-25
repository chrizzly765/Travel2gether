package de.traveltogether.model;

/**
 * Created by Anna-Lena on 27.05.2016.
 */
public class Login {
    String email;
    String password;
    String token;

    public Login(String _email, String _password, String _token){
        email = _email;
        password = _password;
        token = _token;
    }
}
