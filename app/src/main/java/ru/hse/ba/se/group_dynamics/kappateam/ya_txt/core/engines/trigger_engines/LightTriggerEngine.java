package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.engines.trigger_engines;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Trigger;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources.Resources;

/**
 * Класс-движок, реализующий динамическую логику триггеров освещенности.
 */
public class LightTriggerEngine {
    /**
     * Исполнить триггер.
     *
     * @param trigger триггер
     * @return true, если условие триггера выполнено, иначе - false
     */
    public static boolean executeTrigger(Trigger trigger) {
        switch (trigger.getTriggerType()) {
            case LIGHT_LIGHT:
                return Resources.INSTANCE.lightResource.isLight();
            case LIGHT_DARK:
                return Resources.INSTANCE.lightResource.isDark();
            default:
                throw new IllegalStateException("Unsupported trigger type class occurred.");
        }
    }
}
