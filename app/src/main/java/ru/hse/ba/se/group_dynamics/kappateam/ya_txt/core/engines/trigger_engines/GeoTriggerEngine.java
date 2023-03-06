package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.engines.trigger_engines;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Trigger;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources.GeoLocation;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources.ResourceNotAvailableException;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources.Resources;

/**
 * Класс-движок, реализующий динамическую логику геолокационных триггеров.
 */
public class GeoTriggerEngine {

    /**
     * Исполнить триггер.
     *
     * @param trigger триггер
     * @return true, если условие триггера выполнено, иначе - false
     */
    public static boolean executeTrigger(Trigger trigger) throws ResourceNotAvailableException {
        switch (trigger.getTriggerType()) {
            case GEO_IN_CITY:
                return trigger.getTriggerData().equals(Resources.INSTANCE.geoLocation.getCity());
            case GEO_IN_COUNTRY:
                return trigger.getTriggerData().equals(Resources.INSTANCE.geoLocation.getCountry());
            case GEO_NEAR_LOCATION:
                double[] cord1 = GeoLocation.getCoordinates(trigger.getTriggerData());
                double[] cord2 = GeoLocation.getCoordinates(Resources.INSTANCE.geoLocation.getHemisphere());
                return GeoLocation.isNear(cord1[0], cord1[1], cord2[0], cord2[1]);
            default:
                throw new IllegalStateException("Unsupported trigger type class occurred.");
        }
    }
}
