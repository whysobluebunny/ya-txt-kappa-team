package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.engines.trigger_engines;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Trigger;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources.ResourceNotAvailableException;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources.Resources;

/**
 * Класс-движок, реализующий динамическую логику триггеров курса валют.
 */
public class ExchangeRateTriggerEngine {
    /**
     * Исполнить триггер.
     *
     * @param trigger триггер
     * @return true, если условие триггера выполнено, иначе - false
     */
    public static boolean executeTrigger(Trigger trigger) throws ResourceNotAvailableException {
        switch (trigger.getTriggerType()) {
            case EXRATE_RUB_USD_DECREASED_LAST_DAY:
                return Resources.INSTANCE.exchangeRateResource.hasDecreasedInOneDay();
            case EXRATE_RUB_USD_INCREASED_LAST_DAY:
                return Resources.INSTANCE.exchangeRateResource.hasIncreasedInOneDay();
            case EXRATE_RUB_USD_DECREASED_LAST_WEEK:
                return Resources.INSTANCE.exchangeRateResource.hasDecreasedInOneWeek();
            case EXRATE_RUB_USD_INCREASED_LAST_WEEK:
                return Resources.INSTANCE.exchangeRateResource.hasIncreasedInOneWeek();
            case EXRATE_RUB_USD_DECREASED_LAST_MONTH:
                return Resources.INSTANCE.exchangeRateResource.hasDecreasedInOneMonth();
            case EXRATE_RUB_USD_INCREASED_LAST_MONTH:
                return Resources.INSTANCE.exchangeRateResource.hasIncreasedInOneMonth();
            default:
                throw new IllegalStateException("Unsupported trigger type class occurred.");
        }
    }
}
