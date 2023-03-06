package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.engines.variable_engines;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Variable;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources.Resources;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources.TimeResource;

/**
 * Класс-движок, реализующий динамическую логику временных переменных.
 */
public class TimeVariableEngine {
    /**
     * Получить динамический контент для переменной.
     *
     * @param variable переменная
     * @return вычисленный динамический контент для переменной
     */
    public static String getCalculatedContent(Variable variable) {

        switch (variable.getVarType()) {
            case TIME_HOURS:
                return Integer.toString(Resources.INSTANCE.timeResource.getHoursOfDay());
            case TIME_HOURS_MINUTES:
                return TimeResource.getStringFromTime(Resources.INSTANCE.timeResource.getTimeOfDay());
            case TIME_MINUTES:
                return Integer.toString(Resources.INSTANCE.timeResource.getMinutes());
            case TIME_MONTH:
                return Resources.INSTANCE.timeResource.getMonth();
            case TIME_SEASON:
                return Resources.INSTANCE.timeResource.getSeason();
            case TIME_TIMES_OF_DAY:
                return Resources.INSTANCE.timeResource.getDayTime();
            case TIME_WEEKDAY:
                return Resources.INSTANCE.timeResource.getWeekDay();
            default:
                throw new IllegalStateException("Unsupported variable type class occurred.");
        }
    }
}

