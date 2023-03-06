package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.engines.variable_engines;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Variable;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources.Resources;

/**
 * Класс-движок, реализующий динамическую логику переменных освещенности.
 */
public class LightVariableEngine {
    /**
     * Получить динамический контент для переменной.
     *
     * @param variable переменная
     * @return вычисленный динамический контент для переменной
     */
    public static String getCalculatedContent(Variable variable) {

        switch (variable.getVarType()) {
            case LIGHT_TYPE:
                return Resources.INSTANCE.lightResource.getTypeName();
            case LIGHT_LUX:
                return Float.toString(Resources.INSTANCE.lightResource.getLux());
            default:
                throw new IllegalStateException("Unsupported variable type class occurred.");
        }
    }
}

