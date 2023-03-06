package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model;

import android.util.Log;

import lombok.ToString;
/**
 * Переменная — изменчивая часть статичного текста книги.
 */
@ToString(doNotUseGetters = true, callSuper = true)
public class Variable {

    /**
     * Тэг для логгирования.
     */
    private static final String TAG = Variable.class.getName();

    /**
     * Уникальный идентификатор переменной.
     */
    private String varId;
    /**
     * Тип переменной.
     */
    private VariableType varType;
    /**
     * Значение переменной по-умолчанию.
     */
    private String defaultValue;

    /**
     * Блокируем создание полупустых классов.
     */
    private Variable() {
    }

    /**
     * Конструктор переменной.
     *
     * @param varId        идентификатор переменной
     * @param varType      тип переменной
     * @param defaultValue значение по-умолчанию
     */
    public Variable(String varId, VariableType varType, String defaultValue) {
        // проверка параметров
        checkId(varId);
        checkType(varType);
        checkDefaultValue(defaultValue);
        // инициализация полей
        this.varId = varId;
        this.varType = varType;
        this.defaultValue = defaultValue;
        // логирование
        Log.d(TAG, "[cons] Variable successfully created: " + this.toString());
    }

    /**
     * Получить идентификатор переменной.
     *
     * @return идентификатор переменной
     */
    public String getVarId() {
        checkId(this.varId);
        Log.d(TAG, "[getVarId] The variable: " + this.toString() + " has the id of " + this.varId + ", which is returned. ");
        return varId;
    }

    /**
     * Получить тип переменной.
     *
     * @return тип переменной
     */
    public VariableType getVarType() {
        checkType(this.varType);
        Log.d(TAG, "[getVarType] The variable: " + this.toString() + " has the type of " + this.varType + ", which is returned. ");
        return varType;
    }

    /**
     * Получить значение переменной по-умолчанию.
     *
     * @return значение переменной по-умолчанию
     */
    public String getDefaultValue() {
        checkDefaultValue(this.defaultValue);
        Log.d(TAG, "[getDefaultValue] The variable: " + this.toString() + " has the default value of " + this.defaultValue + ", which is returned. ");
        return defaultValue;
    }

    /**
     * Проверка корректности идентификатора.
     *
     * @param id идентификатор
     */
    private void checkId(String id) {
        if (id == null || id.isEmpty()) {
            Log.e(TAG, "[checkId] The id of a variable must be a non-empty string.");
            throw new IllegalArgumentException("The id of a variable must be a non-empty string.");
        }
    }

    /**
     * Проверка корректности типа.
     *
     * @param type тип
     */
    private void checkType(VariableType type) {
        if (type == null) {
            Log.e(TAG, "[checkType] The type of a variable must not be empty.");
            throw new IllegalArgumentException("The type of a variable must not be empty.");
        }
    }

    /**
     * Проверка корректности значения по-умолчанию.
     *
     * @param defaultValue значение по-умолчанию
     */
    private void checkDefaultValue(String defaultValue) {
        if (defaultValue == null || defaultValue.isEmpty()) {
            Log.e(TAG, "[checkDefaultValue] The default value of a variable must be a non-empty string.");
            throw new IllegalArgumentException("The default value of a variable must be a non-empty string.");
        }
    }
}
