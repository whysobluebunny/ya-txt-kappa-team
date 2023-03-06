package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.engines.variable_engines;

import android.content.Context;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Variable;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources.ResourceNotAvailableException;

/**
 * Класс-движок, реализующий динамическую логику переменных.
 */
public class VariableEngine {
    /**
     * Получить динамический контент для переменной.
     *
     * @param variable переменная
     * @return вычисленный динамический контент для переменной
     */
    public static String getCalculatedContent(Variable variable, Context context) {
        try {
            switch (variable.getVarType().getTypeClass()) {
                case TIME:
                    return TimeVariableEngine.getCalculatedContent(variable);
                case GEO:
                    return GeoVariableEngine.getCalculatedContent(variable);
                case WEATHER:
                    return WeatherVariableEngine.getCalculatedContent(variable);
                case PERSONA:
                    return PersonaVariableEngine.getCalculatedContent(variable, context);
                case EXCHANGE_RATE:
                    return ExchangeRateVariableEngine.getCalculatedContent(variable);
                case BTC_RATE:
                    return BitcoinRateVariableEngine.getCalculatedContent(variable);
                case COVID:
                    return CovidVariableEngine.getCalculatedContent(variable);
                case LIGHT:
                    return LightVariableEngine.getCalculatedContent(variable);
                default:
                    throw new IllegalStateException("Unsupported variable type class occurred.");
            }
        } catch (Exception ignored) {
            return variable.getDefaultValue();
        }
    }
}
