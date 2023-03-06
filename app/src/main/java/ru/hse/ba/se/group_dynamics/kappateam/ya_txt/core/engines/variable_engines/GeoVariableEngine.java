package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.engines.variable_engines;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Variable;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources.ResourceNotAvailableException;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources.Resources;

/**
 * Класс-движок, реализующий динамическую логику геолокационных переменных.
 */
public class GeoVariableEngine {
    /**
     * Получить динамический контент для переменной.
     *
     * @param variable переменная
     * @return вычисленный динамический контент для переменной
     */
    public static String getCalculatedContent(Variable variable) throws ResourceNotAvailableException {

        switch (variable.getVarType()) {
            case GEO_CITY:
                return Resources.INSTANCE.geoLocation.getCity();
            case GEO_CONTINENT:
                return Resources.INSTANCE.geoLocation.getContinent();
            case GEO_COUNTRY:
                return Resources.INSTANCE.geoLocation.getCountry();
            case GEO_HEMISPHERE:
                return Resources.INSTANCE.geoLocation.getHemisphere();
            case GEO_STREET:
                return Resources.INSTANCE.geoLocation.getStreet();
            default:
                throw new IllegalStateException("Unsupported variable type class occurred.");
        }
    }
}
