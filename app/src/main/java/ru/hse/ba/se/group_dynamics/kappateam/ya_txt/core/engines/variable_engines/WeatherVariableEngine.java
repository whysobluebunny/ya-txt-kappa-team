package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.engines.variable_engines;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Variable;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources.ResourceNotAvailableException;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources.Resources;

/**
 * Класс-движок, реализующий динамическую логику погодных переменных.
 */
public class WeatherVariableEngine {
    /**
     * Получить динамический контент для переменной.
     *
     * @param variable переменная
     * @return вычисленный динамический контент для переменной
     */
    public static String getCalculatedContent(Variable variable) throws ResourceNotAvailableException {

        switch (variable.getVarType()) {
            case WEATHER_CLASS_OF_PRESSURE:
                double pressure = Resources.INSTANCE.weatherResource.getCurrentPressure() * 0.75006375541921;
                if (pressure < 760)
                    return "Пониженное";
                if (Math.abs(pressure) == 760)
                    return "Нормальное";
                return "Повышенное";
            case WEATHER_CLASS_OF_TEMP:
                double temp = Resources.INSTANCE.weatherResource.getCurrentTemperature();
                if(temp < 5)
                    return "Холодно";
                if(temp < 15)
                    return "Прохладно";
                if(temp < 20)
                    return "Нейтрально";
                if(temp < 28)
                    return "Тепло";
                if(temp < 35)
                    return "Жарко";
                if(temp > 35)
                    return "Пекло";
            case WEATHER_CLASS_OF_WEATHER:
                switch (Resources.INSTANCE.weatherResource.getWeatherType()){
                    case "clear sky":
                        return "Солнечно";
                    case "mist":
                    case "Smoke":
                    case "Haze":
                    case "sand whirls":
                    case "dust whirls":
                    case "sand/ dust whirls":
                    case "fog":
                    case "sand":
                    case "dust":
                        return "Туман";
                    case "few clouds":
                    case "scattered clouds":
                    case "broken clouds":
                    case "overcast clouds":
                        return "Пасмурно";
                    case "Snow":
                    case "light snow":
                    case "Heavy snow":
                    case "Sleet":
                    case "Light shower sleet":
                    case "Shower sleet":
                    case "Light rain and snow":
                    case "Rain and snow":
                    case "Light shower snow":
                    case "Shower snow":
                    case "Heavy shower snow":
                        return "Снег";
                    case "rain":
                    case "shower rain":
                    case "thunderstorm":
                    case "light rain":
                    case "moderate rain":
                    case "heavy intensity rain":
                    case "very heavy rain":
                    case "extreme rain":
                    case "freezing rain":
                    case "light intensity shower rain":
                    case "heavy intensity shower rain":
                    case "ragged shower rain":
                    case "light intensity drizzle":
                    case "drizzle":
                    case "heavy intensity drizzle":
                    case "light intensity drizzle rain":
                    case "drizzle rain":
                    case "heavy intensity drizzle rain":
                    case "shower rain and drizzle":
                    case "heavy shower rain and drizzle":
                    case "shower drizzle":
                    case "thunderstorm with light rain":
                    case "thunderstorm with rain":
                    case "thunderstorm with heavy rain":
                    case "light thunderstorm":
                    case "heavy thunderstorm":
                    case "ragged thunderstorm":
                    case "thunderstorm with light drizzle":
                    case "thunderstorm with drizzle":
                    case "thunderstorm with heavy drizzle":
                        return "Дождь";
                    case "volcanic ash":
                    case "squall":
                    case "tornado":
                        return "Катастрофа";
                    default:
                        throw new ResourceNotAvailableException("Issue parsing weather type occurred");
                }
            default:
                throw new IllegalStateException("Unsupported variable type class occurred.");
        }
    }
}
