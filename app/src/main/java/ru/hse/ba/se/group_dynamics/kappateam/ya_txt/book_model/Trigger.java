package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model;

import android.util.Log;

import lombok.ToString;

/**
 * Класс, представляющий триггер — основу динамики книги.
 */
@ToString(doNotUseGetters = true, callSuper = true)
public class Trigger {

    /**
     * Тэг для логгирования.
     */
    private static final String TAG = Trigger.class.getName();

    /**
     * Идентификатор ноды, на которую ссылается триггер.
     */
    private String refId;

    /**
     * Тип триггера.
     */
    private TriggerType triggerType;

    /**
     * Данные триггера.
     */
    private String triggerData;

    /**
     * Блокируем создание полупустых классов.
     */
    private Trigger() {
    }

    /**
     * Конструктор триггера.
     *
     * @param refId       идентификатор ноды, на которую ссылается триггер
     * @param triggerType тип триггера
     * @param triggerData данные триггера
     */
    public Trigger(String refId, TriggerType triggerType, String triggerData) {
        // проверка параметров
        checkRefId(refId);
        checkTriggerType(triggerType);
        // инициализация класса
        this.refId = refId;
        this.triggerType = triggerType;
        this.triggerData = triggerData;
        // логгирование
        Log.d(TAG, "[cons] Trigger successfully created: " + this.toString());
    }

    public String getRefId() {
        Log.d(TAG, "[getRefUuid] Getting next node uuid");
        return refId;
    }

    public TriggerType getTriggerType() {
        Log.d(TAG, "[getTriggerName] Getting trigger type: " + triggerType);
        return triggerType;
    }

    public String getTriggerData() {
        Log.d(TAG, "[getTriggerData] Getting trigger data: " + triggerData);
        return triggerData;
    }

    /**
     * Получить причину, по которой триггер сработал.
     * @return причина отработки триггера
     */
    public String getTriggerReason() {
        switch (triggerType.getTypeClass()){
            case WEATHER:
                switch (triggerType) {
                    case WEATHER_CLASS_OF_PRESSURE:
                        return "давление " + triggerData.toLowerCase();
                    case WEATHER_CLASS_OF_TEMPERATURE:
                        return "температура " + triggerData.toLowerCase();
                    case WEATHER_CLASS_OF_WEATHER:
                        return "погода " + triggerData.toLowerCase();
                    case WEATHER_HUMIDITY_HIGHER:
                        return "влажность выше " + triggerData;
                    case WEATHER_HUMIDITY_LOWER:
                        return "влажность ниже " + triggerData;
                    case WEATHER_PRESSURE_HIGHER:
                        return "давление выже " + triggerData;
                    case WEATHER_PRESSURE_LOWER:
                        return "давление ниже " + triggerData;
                    case WEATHER_TEMP_HIGHER:
                        return "температура выше " + triggerData;
                    case WEATHER_TEMP_LOWER:
                        return "температура ниже " + triggerData;
                    default:
                        return "";
                }
            case EXCHANGE_RATE:
                switch (triggerType) {
                    case EXRATE_RUB_USD_DECREASED_LAST_DAY:
                        return "курс рубля снизился за сутки";
                    case EXRATE_RUB_USD_DECREASED_LAST_MONTH:
                        return "курс рубля снизился за месяц";
                    case EXRATE_RUB_USD_INCREASED_LAST_MONTH:
                        return "курс рубля вырос за месяц";
                    case EXRATE_RUB_USD_INCREASED_LAST_WEEK:
                        return "курс рубля вырос за неделю";
                    case EXRATE_RUB_USD_DECREASED_LAST_WEEK:
                        return "курс рубля снизился за неделю";
                    case EXRATE_RUB_USD_INCREASED_LAST_DAY:
                        return "курс рубля вырос за сутки";
                    default:
                        return "";
                }
            case TIME:
                switch (triggerType) {
                    case TIME_DAYOFF:
                        return "выходной день";
                    case TIME_EXACT_HOURS:
                        return "сейчас часов: " + triggerData;
                    case TIME_EXACT_HOURS_LATER:
                        return "часов больше " + triggerData;
                    case TIME_EXACT_HOURS_SOONER:
                        return "часов меньше " + triggerData;
                    case TIME_EXACT_MINUTES:
                        return "сейчас минут: " + triggerData;
                    case TIME_EXACT_MINUTES_LATER:
                        return "минут больше " + triggerData;
                    case TIME_EXACT_MINUTES_SOONER:
                        return "минут меньше " + triggerData;
                    case TIME_EXACT_TIME:
                        return "время сейчас: " + triggerData;
                    case TIME_EXACT_TIME_LATER:
                        return "время больше, чем " + triggerData;
                    case TIME_EXACT_TIME_SOONER:
                        return "время меньше, чем " + triggerData;
                    case TIME_HOLIDAY:
                        return "сегодня праздничный день";
                    case TIME_MONTH:
                        return "месяц " + triggerData.toLowerCase();
                    case TIME_PATTERN_EVEN_HOURS:
                        return "четное число часов";
                    case TIME_PATTERN_EVEN_MINUTES:
                        return "четное число минут";
                    case TIME_PATTERN_MINUTES_HOURS_EQUAL:
                        return "число минут и часов совпало";
                    case TIME_PATTERN_MINUTES_HOURS_MIRRORED:
                        return "часы и минуты зеркальны друг другу";
                    case TIME_PATTERN_MINUTES_HOURS_SUMS_EQUAL:
                        return "сумма цифр часов и минут совпадают";
                    case TIME_PATTERN_ODD_HOURS:
                        return "нечетное число часов";
                    case TIME_PATTERN_ODD_MINUTES:
                        return "нечетное число минут";
                    case TIME_SEASON:
                        return "время года " + triggerData.toLowerCase();
                    case TIME_TIMES_OF_DAY:
                        return "время суток " + triggerData.toLowerCase();
                    case TIME_WORKDAY:
                        return "сегодня будний день";
                    default:
                        return "";
                }
            case GEO:
                switch (triggerType) {
                    case GEO_IN_CITY:
                        return "город " + triggerData;
                    case GEO_IN_COUNTRY:
                        return "страна " + triggerData;
                    case GEO_NEAR_LOCATION:
                        return "близость к таинственной точке на карте";
                    default:
                        return "";
                }
            case LIGHT:
                switch (triggerType) {
                    case LIGHT_DARK:
                        return "вокруг темно";
                    case LIGHT_LIGHT:
                        return "вокруг светло";
                    default:
                        return "";
                }
            case TESTING:
                switch (triggerType) {
                    case ALWAYS_FALSE:
                        return "никогда";
                    case ALWAYS_TRUE:
                        return "всегда";
                    default:
                        return "";
                }
            default:
                return "";
        }
    }

    /**
     * Проверка идентификатора, на который ссылается триггер.
     *
     * @param refId идентификатор, на который ссылается триггер
     */
    private void checkRefId(String refId) {
        if (refId == null || refId.isEmpty()) {
            Log.e(TAG, "[checkRefId] The ref id of a trigger must be a non-empty string.");
            throw new IllegalArgumentException("The ref id of a trigger must be a non-empty string.");
        }
    }

    /**
     * Проверка типа триггера.
     *
     * @param type тип триггера
     */
    private void checkTriggerType(TriggerType type) {
        if (type == null) {
            Log.e(TAG, "[checkTriggerType] The type of a trigger must not be empty.");
            throw new IllegalArgumentException("The type of a trigger must not be empty.");
        }
    }
}
