package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model;

import android.util.Log;

import java.util.ArrayList;

import lombok.ToString;

/**
 * Триггерная нода меняет сюжет книги, опираясь на данные внешней среды.
 */
@ToString(doNotUseGetters = true, callSuper = true)
public class TriggerNode extends Node {

    /**
     * Тэг для логгирования.
     */
    private static final String TAG = TriggerNode.class.getName();

    /**
     * Триггеры ноды.
     */
    private ArrayList<Trigger> triggers;

    /**
     * Блокируем создание полупустых классов.
     */
    private TriggerNode() {
    }

    /**
     * Конструктор триггерной ноды.
     *
     * @param id       идентификатор ноды
     * @param triggers список триггеров по убыванию приоритета
     */
    public TriggerNode(String id, ArrayList<Trigger> triggers) {
        super(id);
        // проверки
        checkTriggers(triggers);
        // инициализация класса
        this.triggers = triggers;
        // логгирование
        Log.d(TAG, "[cons] TriggerNode successfully created: " + this.toString());
    }

    /**
     * Получить триггеры ноды.
     *
     * @return триггеры ноды
     */
    public ArrayList<Trigger> getTriggers() {
        checkTriggers(this.triggers);
        Log.d(TAG, "[getTriggers] Getting triggers of a node " + this.toString());
        return triggers;
    }

    /**
     * Проверка триггеров.
     *
     * @param triggers триггеры
     */
    private void checkTriggers(ArrayList<Trigger> triggers) {
        if (triggers == null) {
            Log.e(TAG, "[checkTriggers] The node's triggers must be instantiated.");
            throw new IllegalStateException("The node's triggers must be instantiated.");
        }
        if (triggers.isEmpty()) {
            Log.e(TAG, "[checkTriggers] The node must have at least one trigger.");
            throw new IllegalStateException("The node must have at least one trigger.");
        }
    }

}
