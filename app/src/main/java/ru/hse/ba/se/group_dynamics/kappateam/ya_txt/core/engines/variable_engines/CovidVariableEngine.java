package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.engines.variable_engines;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Variable;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources.ResourceNotAvailableException;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources.Resources;

/**
 * Класс-движок, реализующий динамическую логику переменных статистики ковида.
 */
public class CovidVariableEngine {
    /**
     * Получить динамический контент для переменной.
     *
     * @param variable переменная
     * @return вычисленный динамический контент для переменной
     */
    public static String getCalculatedContent(Variable variable) throws ResourceNotAvailableException {
        int[] data;
        switch (variable.getVarType()) {
            case COVID_SUMMARY_NEW_WORLD:
                data = Resources.INSTANCE.covidResource.getNewWorldData();
                return String.format("За сутки в мире %d заражений, %d смертей, %d выздоровлений", data[0], data[1], data[2]);
            case COVID_SUMMARY_NEW_RUSSIA:
                data = Resources.INSTANCE.covidResource.getNewRussiaData();
                return String.format("За сутки в России %d заражений, %d смертей, %d выздоровлений", data[0], data[1], data[2]);
            case COVID_SUMMARY_TOTAL_WORLD:
                data = Resources.INSTANCE.covidResource.getTotalWorldData();
                return String.format("Всего в мире %d заражений, %d смертей, %d выздоровлений", data[0], data[1], data[2]);
            case COVID_SUMMARY_TOTAL_RUSSIA:
                data = Resources.INSTANCE.covidResource.getTotalRussiaData();
                return String.format("Всего в России %d заражений, %d смертей, %d выздоровлений", data[0], data[1], data[2]);
            case COVID_STAT_NEW_CASES_WORLD:
                return String.valueOf(Resources.INSTANCE.covidResource.getNewWorldData()[0]);
            case COVID_STAT_NEW_CASES_RUSSIA:
                return String.valueOf(Resources.INSTANCE.covidResource.getNewRussiaData()[0]);
            case COVID_STAT_NEW_DEATHS_WORLD:
                return String.valueOf(Resources.INSTANCE.covidResource.getNewWorldData()[1]);
            case COVID_STAT_NEW_DEATHS_RUSSIA:
                return String.valueOf(Resources.INSTANCE.covidResource.getNewRussiaData()[1]);
            case COVID_STAT_TOTAL_CASES_WORLD:
                return String.valueOf(Resources.INSTANCE.covidResource.getTotalWorldData()[0]);
            case COVID_STAT_TOTAL_CASES_RUSSIA:
                return String.valueOf(Resources.INSTANCE.covidResource.getTotalRussiaData()[0]);
            case COVID_STAT_TOTAL_DEATHS_WORLD:
                return String.valueOf(Resources.INSTANCE.covidResource.getTotalWorldData()[1]);
            case COVID_STAT_TOTAL_DEATHS_RUSSIA:
                return String.valueOf(Resources.INSTANCE.covidResource.getTotalRussiaData()[1]);
            case COVID_STAT_NEW_RECOVERED_WORLD:
                return String.valueOf(Resources.INSTANCE.covidResource.getNewWorldData()[2]);
            case COVID_STAT_NEW_RECOVERED_RUSSIA:
                return String.valueOf(Resources.INSTANCE.covidResource.getNewRussiaData()[2]);
            case COVID_STAT_TOTAL_RECOVERED_WORLD:
                return String.valueOf(Resources.INSTANCE.covidResource.getTotalWorldData()[2]);
            case COVID_STAT_TOTAL_RECOVERED_RUSSIA:
                return String.valueOf(Resources.INSTANCE.covidResource.getTotalRussiaData()[2]);
            default:
                throw new IllegalStateException("Unsupported variable type class occurred.");
        }
    }
}
