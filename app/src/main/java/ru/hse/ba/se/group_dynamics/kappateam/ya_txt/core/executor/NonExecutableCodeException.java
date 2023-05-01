package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.executor;

public class NonExecutableCodeException extends RuntimeException {

    public NonExecutableCodeException() {
        super("Code cannot be executed");
    }

    public NonExecutableCodeException(String message) {
        super(message);
    }
}
