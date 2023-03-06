package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.engines.trigger_engines;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Trigger;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources.Resources;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources.TimeResource;

/**
 * Класс-движок, реализующий динамическую логику временных триггеров.
 */
public class TimeTriggerEngine {

    /**
     * Исполнить триггер.
     *
     * @param trigger триггер
     * @return true, если условие триггера выполнено, иначе - false
     */
    public static boolean executeTrigger(Trigger trigger) {
        switch (trigger.getTriggerType()) {
            case TIME_MONTH:
                return trigger.getTriggerData().equals(Resources.INSTANCE.timeResource.getMonth());
            case TIME_DAYOFF:
                return Resources.INSTANCE.timeResource.isWeekend();
            case TIME_SEASON:
                return trigger.getTriggerData().equals(Resources.INSTANCE.timeResource.getSeason());
            case TIME_HOLIDAY:
                return Resources.INSTANCE.timeResource.isHoliday();
            case TIME_WORKDAY:
                return Resources.INSTANCE.timeResource.isWorkDay();
            case TIME_TIMES_OF_DAY:
                return trigger.getTriggerData().equals(Resources.INSTANCE.timeResource.getDayTime());
            case TIME_EXACT_TIME:
                return trigger.getTriggerData().equals(TimeResource.getStringFromTime(Resources.INSTANCE.timeResource.getTime()));
            case TIME_EXACT_HOURS:
                return trigger.getTriggerData().equals(Integer.toString(Resources.INSTANCE.timeResource.getHours()));
            case TIME_EXACT_MINUTES:
                return trigger.getTriggerData().equals(Integer.toString(Resources.INSTANCE.timeResource.getMinutes()));
            case TIME_EXACT_TIME_LATER:
                return trigger.getTriggerData().compareTo(TimeResource.getStringFromTime(Resources.INSTANCE.timeResource.getTime())) > 0;
            case TIME_EXACT_TIME_SOONER:
                return trigger.getTriggerData().compareTo(TimeResource.getStringFromTime(Resources.INSTANCE.timeResource.getTime())) < 0;
            case TIME_EXACT_HOURS_LATER:
                return trigger.getTriggerData().compareTo(Integer.toString(Resources.INSTANCE.timeResource.getHours())) > 0;
            case TIME_EXACT_HOURS_SOONER:
                return trigger.getTriggerData().compareTo(Integer.toString(Resources.INSTANCE.timeResource.getHours())) < 0;
            case TIME_EXACT_MINUTES_LATER:
                return trigger.getTriggerData().compareTo(Integer.toString(Resources.INSTANCE.timeResource.getMinutes())) > 0;
            case TIME_EXACT_MINUTES_SOONER:
                return trigger.getTriggerData().compareTo(Integer.toString(Resources.INSTANCE.timeResource.getMinutes())) < 0;
            case TIME_PATTERN_ODD_HOURS:
                return Resources.INSTANCE.timeResource.getHours() % 2 == 1;
            case TIME_PATTERN_EVEN_HOURS:
                return Resources.INSTANCE.timeResource.getHours() % 2 == 0;
            case TIME_PATTERN_ODD_MINUTES:
                return Resources.INSTANCE.timeResource.getMinutes() % 2 == 1;
            case TIME_PATTERN_EVEN_MINUTES:
                return Resources.INSTANCE.timeResource.getMinutes() % 2 == 0;
            case TIME_PATTERN_MINUTES_HOURS_EQUAL:
                return Resources.INSTANCE.timeResource.getHours() == Resources.INSTANCE.timeResource.getMinutes();
            case TIME_PATTERN_MINUTES_HOURS_MIRRORED:
                int hours = Resources.INSTANCE.timeResource.getHours();
                int minutes = Resources.INSTANCE.timeResource.getMinutes();
                return (hours / 10 == minutes % 10) && (hours % 10 == minutes / 10);
            case TIME_PATTERN_MINUTES_HOURS_SUMS_EQUAL:
                hours = Resources.INSTANCE.timeResource.getHours();
                minutes = Resources.INSTANCE.timeResource.getMinutes();
                return hours / 10 + hours % 10 == minutes / 10 + minutes % 10;
            default:
                throw new IllegalStateException("Unsupported trigger type class occurred.");
        }
    }
}
