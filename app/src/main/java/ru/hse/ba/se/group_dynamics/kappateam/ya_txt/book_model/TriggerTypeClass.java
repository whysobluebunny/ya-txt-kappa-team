package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model;

/**
 * Классы типов триггеров.
 */
public enum TriggerTypeClass {
    // временные триггеры
    TIME("time"),
    // триггеры геопозиции
    GEO("geo"),
    // триггеры курсов валют
    EXCHANGE_RATE("exchange_rate"),
    // триггеры погоды
    WEATHER("weather"),
    // тестирование
    TESTING("testing"),
    // освещенность
    LIGHT("light");

    // строковое значение класса
    private String typeClass;

    /**
     * Инцииализация класса
     *
     * @param typeClass класс
     */
    TriggerTypeClass(String typeClass) {
        if (typeClass == null){
            typeClass = "time";
        }
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
