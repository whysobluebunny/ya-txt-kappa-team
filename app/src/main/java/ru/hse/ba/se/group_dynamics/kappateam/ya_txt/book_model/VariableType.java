package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model;

/**
 * Типы переменных.
 */
public enum VariableType {
    /* ВРЕМЕННЫЕ ПЕРЕМЕННЫЕ */
    /**
     * Подстановка [X], где [X] – точное значение времени.
     */
    TIME_HOURS_MINUTES("time_hours_minutes"),
    /**
     * Подстановка [X], где [X] – точное значение часов.
     */
    TIME_HOURS("time_hours"),
    /**
     * Подстановка [X], где [X] – точное значение минут.
     */
    TIME_MINUTES("time_minutes"),
    /**
     * Подстановка [X], где [X] – текущий день недели.
     */
    TIME_WEEKDAY("time_weekday"),
    /**
     * Подстановка [X], где [X] – текущий месяц.
     */
    TIME_MONTH("time_month"),
    /**
     * Подстановка [X], где [X] – текущее время года.
     */
    TIME_SEASON("time_season"),
    /**
     * Подстановка [X], где [X] – текущее время суток.
     */
    TIME_TIMES_OF_DAY("time_times_of_day"),

    /* ГЕОЛОКАЦИОННЫЕ ПЕРЕМЕННЫЕ */
    /**
     * Подстановка [X], где [X] – текущий город.
     */
    GEO_CITY("geo_city"),
    /**
     * Подстановка [X], где [X] – текущая страна.
     */
    GEO_COUNTRY("geo_country"),
    /**
     * Подстановка [X], где [X] – текущая улица.
     */
    GEO_STREET("geo_street"),
    /**
     * Подстановка [X], где [X] – текущий материк.
     */
    GEO_CONTINENT("geo_continent"),
    /**
     * Подстановка [X], где [X] – текущее полушарие Земли.
     */
    GEO_HEMISPHERE("geo_hemisphere"),

    /* ПЕРЕМЕННЫЕ КУРСОВ ВАЛЮТ */
    /**
     * Текущий курс рубля к доллару.
     */
    EXRATE_FROM_USD_TO_RUB("exrate_from_usd_to_rub"),

    /**
     * Текущий курс рубля к биткоину.
     */
    EXRATE_FROM_BTC_TO_RUB("exrate_from_btc_to_rub"),

    /* ПОГОДНЫЕ ПЕРЕМЕННЫЕ */
    /**
     * Подстановка [X], где [X] – значение погоды. Возможные значения:
     * <p>
     * Туман.
     * Дождь.
     * Солнечно.
     * Ветренно.
     * Пасмурно.
     * Ясно.
     * Снег.
     * Град.
     */
    WEATHER_CLASS_OF_WEATHER("weather_class_of_weather"),
    /**
     * Подстановка [X], где [X] – характеристика давления. Возможные значения:
     * <p>
     * Повышенное давление.
     * Пониженное давление.
     * Нормальное давление.
     */
    WEATHER_CLASS_OF_PRESSURE("weather_class_of_pressure"),
    /**
     * Подстановка [X], где [X] – характеристика температуры. Возможные значения:
     * <p>
     * Холодно.
     * Прохладно.
     * Нейтрально.
     * Тепло.
     * Жарко.
     * Пекло.
     */
    WEATHER_CLASS_OF_TEMP("weather_class_of_temp"),

    /* ПЕРСОНИФИЦИРОВАННЫЕ ПЕРЕМЕННЫЕ */
    /**
     * Подстановка [X], где [X] – ответ пользователя на вопрос: В каком городе вы родились?
     */
    PERSONA_HOMETOWN("persona_hometown"),
    /**
     * Подстановка [X], где [X] – ответ пользователя на вопрос: Какой у вас пол?
     */
    PERSONA_GENDER("persona_gender"),
    /**
     * Подстановка [X], где [X] – ответ пользователя на вопрос: Какой у вас возраст?
     */
    PERSONA_AGE("persona_age"),
    /**
     * Подстановка [X], где [X] – ответ пользователя на вопрос: Какими языками вы владеете? (один из языков)
     */
    PERSONA_SPOKEN_LANGUAGES("persona_spoken_languages"),
    /**
     * Подстановка [X], где [X] – ответ пользователя на вопрос: Ваш предпочитаемый музыкальный жанр?
     */
    PERSONA_MUSIC_GENRE("persona_music_genre"),

    /* СТАТИСТИЧЕСКАЯ ПЕРЕМЕННАЯ КОВИД-19 */
    /**
     * Подстановка За сутки в России [X] заражений, [Y] смертей, [Z] выздоровлений, где [X] – количество новых
     * заражений за сутки, [Y] - смертей, [Z] - выздоровлений.
     */
    COVID_SUMMARY_NEW_RUSSIA("covid_summary_new_russia"),
    /**
     * Подстановка За сутки в мире [X] заражений, [Y] смертей, [Z] выздоровлений, где [X] – количество новых
     * заражений за сутки, [Y] - смертей, [Z] - выздоровлений.
     */
    COVID_SUMMARY_NEW_WORLD("covid_summary_new_world"),
    /**
     * Подстановка Всего в России [X] заражений, [Y] смертей, [Z] выздоровлений, где [X] – количество новых
     * заражений за сутки, [Y] - смертей, [Z] - выздоровлений.
     */
    COVID_SUMMARY_TOTAL_RUSSIA("covid_summary_total_russia"),
    /**
     * Подстановка Всего в мире [X] заражений, [Y] смертей, [Z] выздоровлений, где [X] – количество новых
     * заражений за сутки, [Y] - смертей, [Z] - выздоровлений.
     */
    COVID_SUMMARY_TOTAL_WORLD("covid_summary_total_world"),
    /**
     * Подстановка [X], где [X] - количество новых заражений в России
     */
    COVID_STAT_NEW_CASES_RUSSIA("covid_stat_new_cases_russia"),
    /**
     * Подстановка [X], где [X] - количество новых смертей в России
     */
    COVID_STAT_NEW_DEATHS_RUSSIA("covid_stat_new_deaths_russia"),
    /**
     * Подстановка [X], где [X] - количество новых выздоровлений в России
     */
    COVID_STAT_NEW_RECOVERED_RUSSIA("covid_stat_new_recovered_russia"),
    /**
     * Подстановка [X], где [X] - количество новых заражений в Мире
     */
    COVID_STAT_NEW_CASES_WORLD("covid_stat_new_cases_world"),
    /**
     * Подстановка [X], где [X] - количество новых смертей в Мире
     */
    COVID_STAT_NEW_DEATHS_WORLD("covid_stat_new_deaths_world"),
    /**
     * Подстановка [X], где [X] - количество новых выздоровлений в Мире
     */
    COVID_STAT_NEW_RECOVERED_WORLD("covid_stat_new_recovered_world"),
    /**
     * Подстановка [X], где [X] - общее количество заражений в России
     */
    COVID_STAT_TOTAL_CASES_RUSSIA("covid_stat_total_cases_russia"),
    /**
     * Подстановка [X], где [X] - общее количество смертей в России
     */
    COVID_STAT_TOTAL_DEATHS_RUSSIA("covid_stat_total_deaths_russia"),
    /**
     * Подстановка [X], где [X] - общее количество выздоровлений в России
     */
    COVID_STAT_TOTAL_RECOVERED_RUSSIA("covid_stat_total_recovered_russia"),
    /**
     * Подстановка [X], где [X] - общее количество заражений в Мире
     */
    COVID_STAT_TOTAL_CASES_WORLD("covid_stat_total_cases_world"),
    /**
     * Подстановка [X], где [X] - общее количество смертей в Мире
     */
    COVID_STAT_TOTAL_DEATHS_WORLD("covid_stat_total_deaths_world"),
    /**
     * Подстановка [X], где [X] - общее количество выздоровлений в Мире
     */
    COVID_STAT_TOTAL_RECOVERED_WORLD("covid_stat_total_recovered_world"),
    /**
     * Подстановка [X], где [X] – освещенность. Возможные значения:
     * <p>
     * Cветло.
     * Темно.
     */
    LIGHT_TYPE("light_type"),
    /**
     * Подстановка [X], где [X] – освещенность в люксах.
     */
    LIGHT_LUX("light_lux");

    // строковое значение типа
    private String type;

    /**
     * Инициализовать тип.
     *
     * @param type тип
     */
    VariableType(String type) {
        this.type = type;
    }

    /**
     * Получить строковое значение типа.
     *
     * @return строковое значение типа
     */
    public String getTypeString() {
        return this.type;
    }

    /**
     * Получить класс типа.
     *
     * @return класс типа
     */
    public VariableTypeClass getTypeClass() {
        switch (this) {
            case TIME_HOURS:
            case TIME_HOURS_MINUTES:
            case TIME_MINUTES:
            case TIME_MONTH:
            case TIME_SEASON:
            case TIME_TIMES_OF_DAY:
            case TIME_WEEKDAY:
                return VariableTypeClass.TIME;
            case GEO_CITY:
            case GEO_CONTINENT:
            case GEO_COUNTRY:
            case GEO_HEMISPHERE:
            case GEO_STREET:
                return VariableTypeClass.GEO;
            case WEATHER_CLASS_OF_PRESSURE:
            case WEATHER_CLASS_OF_TEMP:
            case WEATHER_CLASS_OF_WEATHER:
                return VariableTypeClass.WEATHER;
            case PERSONA_AGE:
            case PERSONA_GENDER:
            case PERSONA_HOMETOWN:
            case PERSONA_MUSIC_GENRE:
            case PERSONA_SPOKEN_LANGUAGES:
                return VariableTypeClass.PERSONA;
            case EXRATE_FROM_USD_TO_RUB:
                return VariableTypeClass.EXCHANGE_RATE;
            case EXRATE_FROM_BTC_TO_RUB:
                return VariableTypeClass.BTC_RATE;
            case COVID_STAT_NEW_CASES_RUSSIA:
            case COVID_SUMMARY_NEW_WORLD:
            case COVID_SUMMARY_NEW_RUSSIA:
            case COVID_SUMMARY_TOTAL_WORLD:
            case COVID_STAT_NEW_CASES_WORLD:
            case COVID_SUMMARY_TOTAL_RUSSIA:
            case COVID_STAT_NEW_DEATHS_WORLD:
            case COVID_STAT_NEW_DEATHS_RUSSIA:
            case COVID_STAT_TOTAL_CASES_WORLD:
            case COVID_STAT_TOTAL_CASES_RUSSIA:
            case COVID_STAT_TOTAL_DEATHS_WORLD:
            case COVID_STAT_NEW_RECOVERED_WORLD:
            case COVID_STAT_TOTAL_DEATHS_RUSSIA:
            case COVID_STAT_NEW_RECOVERED_RUSSIA:
            case COVID_STAT_TOTAL_RECOVERED_WORLD:
            case COVID_STAT_TOTAL_RECOVERED_RUSSIA:
                return VariableTypeClass.COVID;
            case LIGHT_TYPE:
            case LIGHT_LUX:
                return VariableTypeClass.LIGHT;
            default:
                throw new IllegalStateException("Unsupported variable type occurred.");
        }
    }
}
