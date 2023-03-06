package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources;

/**
 * Исключение, вызываемое в случае, если ресурс не доступен.
 */
public class ResourceNotAvailableException extends Exception {
    public ResourceNotAvailableException(String errorMessage){
        super(errorMessage);
    }
}
