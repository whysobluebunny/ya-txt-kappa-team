package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.engines.trigger_engines;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Trigger;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources.ResourceNotAvailableException;

/**
 * Класс-движок, реализующий динамическую логику триггера.
 */
public class TriggerEngine {

    /**
     * Исполнить триггер.
     *
     * @param trigger триггер
     * @return true, если условие триггера выполнено, иначе - false
     */
    public static boolean executeTrigger(Trigger trigger) {
        try {
            switch (trigger.getTriggerType().getTypeClass()) {
                case TIME:
                    return TimeTriggerEngine.executeTrigger(trigger);
                case GEO:
                    return GeoTriggerEngine.executeTrigger(trigger);
                case EXCHANGE_RATE:
                    return ExchangeRateTriggerEngine.executeTrigger(trigger);
                case WEATHER:
                    return WeatherTriggerEngine.executeTrigger(trigger);
                case TESTING:
                    return TestingTriggerEngine.executeTrigger(trigger);
                case LIGHT:
                    return LightTriggerEngine.executeTrigger(trigger);
                default:
                    throw new IllegalStateException("Unsupported trigger type class occurred.");
            }

        } catch (ResourceNotAvailableException ignored) {
            return false;
        }
    }
}
