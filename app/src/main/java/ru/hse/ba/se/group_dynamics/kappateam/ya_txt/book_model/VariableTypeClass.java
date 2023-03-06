package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model;

/**
 * Классы типов переменных.
 */
public enum VariableTypeClass {
    // временные переменные
    TIME("time"),
    // геопеременные
    GEO("geo"),
    // переменные курсов валют
    EXCHANGE_RATE("exchange_rate"),
    // переменные курса биткоина
    BTC_RATE("btc_rate"),
    // переменные погоды
    WEATHER("weather"),
    // персонифицированные переменные
    PERSONA("persona"),
    // переменная статистики ковида
    COVID("covid"),
    // освещенность
    LIGHT("light");

    // строковое значение класса
    private String typeClass;

    /**
     * Инцииализация класса
     *
     * @param typeClass класс
     */
    VariableTypeClass(String typeClass) {
        this.typeClass = typeClass;
    }

    /**
     * Получить строковое представление класса.
     *
     * @return строковое представление класса
     */
    String getTypeClass() {
        return this.typeClass;
    }
}
