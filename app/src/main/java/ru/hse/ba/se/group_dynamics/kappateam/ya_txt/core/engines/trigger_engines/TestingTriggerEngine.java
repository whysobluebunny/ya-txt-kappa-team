package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.engines.trigger_engines;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Trigger;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources.ResourceNotAvailableException;

/**
 * Класс-движок, реализующий динамическую логику тестовых триггеров.
 */
public class TestingTriggerEngine {

    /**
     * Исполнить триггер.
     *
     * @param trigger триггер
     * @return true, если условие триггера выполнено, иначе - false
     */
    public static boolean executeTrigger(Trigger trigger) throws ResourceNotAvailableException {
        switch (trigger.getTriggerType()) {
            case ALWAYS_TRUE:
                return true;
            case ALWAYS_FALSE:
                return false;
            default:
                throw new IllegalStateException("Unsupported trigger type class occurred.");
        }
    }
}
