package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.engines.trigger_engines;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Trigger;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources.ResourceNotAvailableException;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources.Resources;

/**
 * Класс-движок, реализующий динамическую логику погодных триггеров.
 */
public class WeatherTriggerEngine {
    /**
     * Исполнить триггер.
     *
     * @param trigger триггер
     * @return true, если условие триггера выполнено, иначе - false
     */
    public static boolean executeTrigger(Trigger trigger) throws ResourceNotAvailableException {
        switch (trigger.getTriggerType()) {
            case WEATHER_TEMP_LOWER:
                return Double.parseDouble(trigger.getTriggerData()) < Resources.INSTANCE.weatherResource.getCurrentTemperature();
            case WEATHER_TEMP_HIGHER:
                return Double.parseDouble(trigger.getTriggerData()) > Resources.INSTANCE.weatherResource.getCurrentTemperature();
            case WEATHER_HUMIDITY_LOWER:
                return Integer.parseInt(trigger.getTriggerData()) < Resources.INSTANCE.weatherResource.getCurrentHumidity();
            case WEATHER_PRESSURE_LOWER:
                return Integer.parseInt(trigger.getTriggerData()) < Resources.INSTANCE.weatherResource.getCurrentPressure();
            case WEATHER_HUMIDITY_HIGHER:
                return Integer.parseInt(trigger.getTriggerData()) > Resources.INSTANCE.weatherResource.getCurrentHumidity();
            case WEATHER_PRESSURE_HIGHER:
                return Integer.parseInt(trigger.getTriggerData()) > Resources.INSTANCE.weatherResource.getCurrentPressure();
            case WEATHER_CLASS_OF_WEATHER:
                return trigger.getTriggerData().equals(Resources.INSTANCE.weatherResource.getWeatherType());
            case WEATHER_CLASS_OF_PRESSURE:
                double pressure = Resources.INSTANCE.weatherResource.getCurrentPressure() * 0.75006375541921;
                String type;
                if (pressure < 760)
                    type = "Пониженное";
                else if (Math.abs(pressure) == 760)
                    type = "Нормальное";
                else
                    type = "Повышенное";
                return trigger.getTriggerData().equals(type);
            case WEATHER_CLASS_OF_TEMPERATURE:
                return trigger.getTriggerData().equals(Resources.INSTANCE.weatherResource.getCurrentTemperature() < 0 ? "Положительная" : "Отрицательная");
            default:
                throw new IllegalStateException("Unsupported trigger type class occurred.");
        }
    }
}
