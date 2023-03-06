package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.engines.variable_engines;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Variable;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources.ResourceNotAvailableException;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources.Resources;

/**
 * Класс-движок, реализующий динамическую логику переменных курса биткоина.
 */
public class BitcoinRateVariableEngine {
    /**
     * Получить динамический контент для переменной.
     *
     * @param variable переменная
     * @return вычисленный динамический контент для переменной
     */
    public static String getCalculatedContent(Variable variable) throws ResourceNotAvailableException {

        switch (variable.getVarType()) {
            case EXRATE_FROM_BTC_TO_RUB:
                return String.valueOf(Resources.INSTANCE.bitcoinResource.getExchangeRate());
            default:
                throw new IllegalStateException("Unsupported variable type class occurred.");
        }
    }
}
