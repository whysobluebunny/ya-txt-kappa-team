package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.auth.firebase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.auth.AuthData;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.auth.AuthException;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.auth.AuthRepository;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.auth.AuthUser;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.auth.AuthResult;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.auth.EmailPasswordAuth;

public class FirebaseAuth implements AuthRepository {
    public FirebaseAuth(com.google.firebase.auth.FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    private com.google.firebase.auth.FirebaseAuth mAuth;
    private FirebaseUser user;


    @Override
    public Task<AuthResult> register(AuthData data) throws AuthException {

        try {
            Task<com.google.firebase.auth.AuthResult> resultTask = null;
            if (data instanceof EmailPasswordAuth) {
                EmailPasswordAuth dataClr = (EmailPasswordAuth) data;
                resultTask = mAuth.createUserWithEmailAndPassword(dataClr.email, dataClr.password);
            } else {
                throw new AuthException("not supported auth method");
            }

            return resultTask.continueWith(res -> {
                if (!res.isSuccessful() || res.getResult() == null) {
                    throw new AuthException("auth request was not successful: " + res.getException().getMessage());
                }
                if (res.getResult().getUser() == null) {
                    throw new AuthException("auth user was null");
                }

                return new AuthResult(true);
            });
        } catch (Exception e) {
            throw new AuthException(e.getMessage());
        }
    }

    @Override
    public Task<AuthResult> login(AuthData data) throws AuthException {
        try {
            Task<com.google.firebase.auth.AuthResult> resultTask = null;
            if (data instanceof EmailPasswordAuth) {
                EmailPasswordAuth dataClr = (EmailPasswordAuth) data;
                resultTask = mAuth.signInWithEmailAndPassword(dataClr.email, dataClr.password);
            } else if (data instanceof FirebaseCredentialsAuth) {
                resultTask = mAuth.signInWithCredential(((FirebaseCredentialsAuth) data).credential);
            } else {
                throw new AuthException("not supported auth method");
            }

            return resultTask.continueWith(res -> {
                if (!res.isSuccessful() || res.getResult() == null) {
                    throw new AuthException("auth request was not successful");
                }
                if (res.getResult().getUser() == null) {
                    throw new AuthException("auth user was null");
                }

                user = res.getResult().getUser();

                return new AuthResult(true);
            });

        } catch (Exception e) {
            throw new AuthException(e.getMessage());
        }
    }

    @Override
    public AuthUser getCurrentUser() {
        if (user == null) {
            return null;
        }

        return new AuthUser(user.getDisplayName());
    }

    @Override
    public void logOut() {
        user = null;
    }
}
