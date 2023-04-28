package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.auth.AuthRepository;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.auth.firebase.FirebaseAuth;

public class ServiceLocator {

    private static ServiceLocator instance = null;

    private static AuthRepository authRepository = null;

    private ServiceLocator() {}

    public static ServiceLocator getInstance() {
        if (instance == null) {
            synchronized(ServiceLocator.class) {
                instance = new ServiceLocator();
            }
        }
        return instance;
    }

    public AuthRepository getAuthRepository() {
        if (authRepository == null) {
            synchronized(FirebaseAuth.class) {
                authRepository = new FirebaseAuth(com.google.firebase.auth.FirebaseAuth.getInstance());
            }
        }
        return authRepository;
    }
}

