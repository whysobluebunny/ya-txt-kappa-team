package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.auth.firebase;

import com.google.firebase.auth.AuthCredential;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.auth.AuthData;

public class FirebaseCredentialsAuth extends AuthData {
    AuthCredential credential;

    public FirebaseCredentialsAuth(AuthCredential credential) {
        this.credential = credential;
    }
}
