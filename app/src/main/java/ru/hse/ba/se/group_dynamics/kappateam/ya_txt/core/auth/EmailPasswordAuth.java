package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.auth;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.auth.AuthData;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.auth.AuthException;

public class EmailPasswordAuth extends AuthData {
    public String email;
    public String password;


    public EmailPasswordAuth(String email, String password) throws AuthException {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new AuthException("email and password is required");
        }
        if (password.length() < 6) {
            throw new AuthException("password should be at least 6 characters");
        }
        this.email = email;
        this.password = password;
    }
}

