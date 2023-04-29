package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.auth;

import com.google.android.gms.tasks.Task;

public interface AuthRepository {
    Task<AuthResult> register(AuthData data) throws AuthException;
    Task<AuthResult> login(AuthData data) throws AuthException;

    AuthUser getCurrentUser();
    void logOut();
}



