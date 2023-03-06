package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.utils.logging;

/**
 * Интерфейс, абстрагирующий логгер.
 */
public interface ILogger {
    /**
     * Логгирование ошибки.
     *
     * @param tag     тэг лога
     * @param message сообщение лога
     */
    void logError(String tag, String message);

    /**
     * Логгирование информационного сообщения.
     *
     * @param tag     тэг лога
     * @param message сообщение лога
     */
    void logInfo(String tag, String message);

    /**
     * Логгирование отладочного сообщения.
     *
     * @param tag     тэг лога
     * @param message сообщение лога
     */
    void logDebug(String tag, String message);
}
