package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model;

/**
 * Типы триггеров.
 */
public enum TriggerType {

    /* ПОГОДНЫЕ ТРИГГЕРЫ */

    /**
     * Условие: «Сейчас [X] погода». Где [X] — значение, которое может принимать значения:
     * <p>
     * «Солнечная».
     * «Туманная».
     * «Дождливая».
     * «Ясная».
     * «Снежная».
     */
    WEATHER_CLASS_OF_WEATHER("weather_class_of_weather"),
    /**
     * Условие «Давление [X]». Где [X] — значение, которое может принимать значения:
     * <p>
     * «Повышенное».
     * «Пониженное».
     * «Нормальное».
     */
    WEATHER_CLASS_OF_PRESSURE("weather_class_of_pressure"),
    /**
     * Условие «Температура [X]». Где [X] — значение, которое может принимать значения:
     * <p>
     * «Положительная» (0+).
     * «Отрицательная».
     */
    WEATHER_CLASS_OF_TEMPERATURE("weather_class_of_temperature"),
    /**
     * Условие: «Температура выше [X]», где [X] — значение в градусах Цельсия.
     */
    WEATHER_TEMP_HIGHER("weather_temp_higher"),
    /**
     * Условие: «Температура ниже [X]», где [X] — значение в градусах Цельсия.
     */
    WEATHER_TEMP_LOWER("weather_temp_lower"),
    /**
     * Условие: «Давление выше [X]», где [X] — значение в мм. рт. ст.
     */
    WEATHER_PRESSURE_HIGHER("weather_pressure_higher"),
    /**
     * Уловие: «Давление ниже [X]», где [X] — значение в мм. рт. ст.
     */
    WEATHER_PRESSURE_LOWER("weather_pressure_lower"),
    /**
     * Условие: «Влажность выше [X]», где [X] — значение в процентах.
     */
    WEATHER_HUMIDITY_HIGHER("weather_humidity_higher"),
    /**
     * Условие: «Влажность ниже [X]», где [X] — значение в процентах.
     */
    WEATHER_HUMIDITY_LOWER("weather_humidity_lower"),

    /* ВРЕМЕННЫЕ ТРИГГЕРЫ */

    /**
     * Условие: «Время сейчас — [X]», где [X] — значение времени (часы и минуты).
     */
    TIME_EXACT_TIME("time_exact_time"),
    /**
     * Условие: «Время сейчас позже, чем [X]», где [X] — значение времени (часы и минуты).
     */
    TIME_EXACT_TIME_LATER("time_exact_time_later"),
    /**
     * Условие: «Время сейчас раньше, чем [X]», где [X] — значение времени (часы и минуты).
     */
    TIME_EXACT_TIME_SOONER("time_exact_time_sooner"),
    /**
     * Условие: «Часы сейчас —  [X]», где [X] — значение времени (часы).
     */
    TIME_EXACT_HOURS("time_exact_hours"),
    /**
     * Условие: «Часы сейчас больше, чем [X]», где [X] — значение времени (часы).
     */
    TIME_EXACT_HOURS_LATER("time_exact_hours_later"),
    /**
     * Условие: «Часы сейчас меньше, чем [X]», где [X] — значение времени (часы).
     */
    TIME_EXACT_HOURS_SOONER("time_exact_hours_sooner"),
    /**
     * Условие: «Минуты сейчас —  [X]», где [X] — значение времени (минуты).
     */
    TIME_EXACT_MINUTES("time_exact_minutes"),
    /**
     * Условие: «Минут сейчас больше, чем [X]», где [X] — значение времени (минуты).
     */
    TIME_EXACT_MINUTES_LATER("time_exact_minutes_later"),
    /**
     * Условие: «Минут сейчас меньше, чем [X]», где [X] — значение времени (минуты).
     */
    TIME_EXACT_MINUTES_SOONER("time_exact_minutes_sooner"),
    /**
     * Условие: «Сейчас четное число минут».
     */
    TIME_PATTERN_EVEN_MINUTES("time_pattern_even_minutes"),
    /**
     * Условие: «Сейчас нечетное число минут».
     */
    TIME_PATTERN_ODD_MINUTES("time_pattern_odd_minutes"),
    /**
     * Условие: «Сейчас четное число часов».
     */
    TIME_PATTERN_EVEN_HOURS("time_pattern_even_hours"),
    /**
     * Условие: «Сейчас нечетное число часов».
     */
    TIME_PATTERN_ODD_HOURS("time_pattern_odd_hours"),
    /**
     * Условие: «Число минут и число часов совпадает».
     * В данном случае время рассматривается в 12-часовом формате и
     * в 24-часовом формате для проверки триггера в обеих системах.
     */
    TIME_PATTERN_MINUTES_HOURS_EQUAL("time_pattern_minutes_hours_equal"),
    /**
     * Условие: «Число минут и число часов зеркально отражены». Примеры: 21:12 или 10:01.
     * В данном случае время рассматривается в 12-часовом формате и в
     * 24-часовом формате для проверки триггера в обеих системах.
     */
    TIME_PATTERN_MINUTES_HOURS_MIRRORED("time_pattern_minutes_hours_mirrored"),
    /**
     * Условие: «Сумма цифр числа минут совпадает с суммой цифр числа часов».
     * В данном случае время рассматривается в 12-часовом формате и
     * в 24-часовом формате для проверки триггера в обеих системах.
     */
    TIME_PATTERN_MINUTES_HOURS_SUMS_EQUAL("time_pattern_minutes_hours_sums_equal"),
    /**
     * Условие: «Сегодня — праздничный день».
     */
    TIME_HOLIDAY("time_holiday"),
    /**
     * Условие: «Сегодня — будний день».
     */
    TIME_WORKDAY("time_workday"),
    /**
     * Условие: «Сегодня — выходной день».
     */
    TIME_DAYOFF("time_dayoff"),
    /**
     * Условие: «Время года — [X]», где [X] может принимать значения:
     * <p>
     * «Зима».
     * «Весна».
     * «Лето».
     * «Осень».
     */
    TIME_SEASON("time_season"),
    /**
     * Условие: «Время суток — [X]», где [X] может принимать значения:
     * <p>
     * «Ночь».
     * «Утро».
     * «День».
     * «Вечер».
     */
    TIME_TIMES_OF_DAY("time_times_of_day"),
    /**
     * Условие: «Месяц — [X]», где [X] может принимать значения:
     * <p>
     * «Январь».
     * «Февраль».
     * «Март».
     * «Апрель».
     * «Май».
     * «Июнь».
     * «Июль».
     * «Август».
     * «Сентябрь».
     * «Октябрь».
     * «Ноябрь».
     * «Декабрь».
     */
    TIME_MONTH("time_month"),

    /* ТРИГГЕРЫ КУРСОВ ВАЛЮТ */

    /**
     * Условие: «Курс рубля к доллару понизился за последний день».
     */
    EXRATE_RUB_USD_DECREASED_LAST_DAY("exrate_rub_usd_decreased_last_day"),
    /**
     * Условие: «Курс рубля к доллару повысился за последний день».
     */
    EXRATE_RUB_USD_INCREASED_LAST_DAY("exrate_rub_usd_increased_last_day"),
    /**
     * Условие: «Курс рубля к доллару понизился за последний месяц».
     */
    EXRATE_RUB_USD_DECREASED_LAST_MONTH("exrate_rub_usd_decreased_last_month"),
    /**
     * Условие: «Курс рубля к доллару повысился за последний месяц».
     */
    EXRATE_RUB_USD_INCREASED_LAST_MONTH("exrate_rub_usd_increased_last_month"),
    /**
     * Условие: «Курс рубля к доллару понизился за последнюю неделю».
     */
    EXRATE_RUB_USD_DECREASED_LAST_WEEK("exrate_rub_usd_decreased_last_week"),
    /**
     * Условие: «Курс рубля к доллару повысился за последнюю неделю».
     */
    EXRATE_RUB_USD_INCREASED_LAST_WEEK("exrate_rub_usd_increased_last_week"),

    /* ГЕОЛОКАЦИОННЫЕ ТРИГГЕРЫ */
    /**
     * Условие: «Читатель находится в стране [X]», где [X] — страна.
     */
    GEO_IN_COUNTRY("geo_in_country"),
    /**
     * Условие: «Читатель находится в городе [X]», где [X] — город.
     */
    GEO_IN_CITY("geo_in_city"),
    /**
     * Условие: «Читатель близок к заданной геоточке на карте».
     * Точность измерений в таком триггере принимается за 500 метров.
     */
    GEO_NEAR_LOCATION("geo_near_location"),
    /**
     * Условие: «Всегда правдиво».
     */
    ALWAYS_TRUE("always_true"),
    /**
     * Условие: «Всегда ложно».
     */
    ALWAYS_FALSE("always_false"),
    /**
     * Условие: «Если сейчас светло.».
     */
    LIGHT_LIGHT("light_dark"),
    /**
     * Условие: «Если сейчас темно.».
     */
    LIGHT_DARK("light_light");


    // строковое представление типа
    private String type;

    /**
     * Инициализация типа
     *
     * @param type строковое представление типа
     */
    TriggerType(String type) {
        this.type = type;
    }

    /**
     * Получить строковое представление типа.
     *
     * @return строковое представление типа
     */
    String getType() {
        return type;
    }

    /**
     * Получить класс типа.
     *
     * @return класс типа
     */
    public TriggerTypeClass getTypeClass() {
        switch (this) {
            case TIME_MONTH:
            case TIME_DAYOFF:
            case TIME_SEASON:
            case TIME_HOLIDAY:
            case TIME_WORKDAY:
            case TIME_EXACT_TIME:
            case TIME_EXACT_HOURS:
            case TIME_TIMES_OF_DAY:
            case TIME_EXACT_MINUTES:
            case TIME_EXACT_TIME_LATER:
            case TIME_EXACT_HOURS_LATER:
            case TIME_EXACT_TIME_SOONER:
            case TIME_PATTERN_ODD_HOURS:
            case TIME_EXACT_HOURS_SOONER:
            case TIME_PATTERN_EVEN_HOURS:
            case TIME_EXACT_MINUTES_LATER:
            case TIME_PATTERN_ODD_MINUTES:
            case TIME_EXACT_MINUTES_SOONER:
            case TIME_PATTERN_EVEN_MINUTES:
            case TIME_PATTERN_MINUTES_HOURS_EQUAL:
            case TIME_PATTERN_MINUTES_HOURS_MIRRORED:
            case TIME_PATTERN_MINUTES_HOURS_SUMS_EQUAL:
                return TriggerTypeClass.TIME;
            case GEO_IN_CITY:
            case GEO_IN_COUNTRY:
            case GEO_NEAR_LOCATION:
                return TriggerTypeClass.GEO;
            case WEATHER_TEMP_LOWER:
            case WEATHER_TEMP_HIGHER:
            case WEATHER_HUMIDITY_LOWER:
            case WEATHER_PRESSURE_LOWER:
            case WEATHER_HUMIDITY_HIGHER:
            case WEATHER_PRESSURE_HIGHER:
            case WEATHER_CLASS_OF_WEATHER:
            case WEATHER_CLASS_OF_PRESSURE:
            case WEATHER_CLASS_OF_TEMPERATURE:
                return TriggerTypeClass.WEATHER;
            case EXRATE_RUB_USD_DECREASED_LAST_DAY:
            case EXRATE_RUB_USD_INCREASED_LAST_DAY:
            case EXRATE_RUB_USD_DECREASED_LAST_WEEK:
            case EXRATE_RUB_USD_INCREASED_LAST_WEEK:
            case EXRATE_RUB_USD_DECREASED_LAST_MONTH:
            case EXRATE_RUB_USD_INCREASED_LAST_MONTH:
                return TriggerTypeClass.EXCHANGE_RATE;
            case ALWAYS_TRUE:
            case ALWAYS_FALSE:
                return TriggerTypeClass.TESTING;
            case LIGHT_LIGHT:
            case LIGHT_DARK:
                return TriggerTypeClass.LIGHT;
            default:
                throw new IllegalStateException("Unsupported trigger type occurred.");
        }
    }
}