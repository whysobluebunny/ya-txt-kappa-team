package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.interfaces;

import java.io.IOException;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources.ResourceNotAvailableException;

/**
 * Интерфейс, абстрагирующий методы обработчика событий View интерфейса IHtmlView.
 */
public interface IHtmlViewEventsHandler {

    /**
     * Обработчик события открытия книги
     * @param callbackObject обьект показа HTML кода
     * @param bookId идентификатор книги
     */
    void onBookOpen(IHtmlView callbackObject, String bookId);

    /**
     * Обработчик события закрытия книги
     * @param bookId идентификатор книги
     * @param htmlContent текущее содержимое книги
     */
    void onBookClose(String bookId, String htmlContent, IHtmlView callbackObject);

    /**
     * Обработчик события достижения (появления на экране) триггерной ноды
     * @param callbackObject обьект показа HTML кода
     * @param nodeId ID триггерной ноды
     */
    void onTriggerNodeAppearance(IHtmlView callbackObject, String nodeId);

    /**
     * Обработчик события достижения (появления на экране) исполняемой ноды
     * @param callbackObject обьект показа HTML кода
     * @param nodeId ID исполняемой ноды
     */
    void onExecutableNodeAppearance(IHtmlView callbackObject, String nodeId);

    /**
     * Обработчик события достижения (появления на экране) переменной
     * @param callbackObject обьект показа HTML кода
     * @param varId ID переменной
     */
    void onVariableAppearance(IHtmlView callbackObject, String varId);
}
