package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model;

import android.util.Log;

import lombok.ToString;

/**
 * Нода — структурная единица книги.
 */
@ToString(doNotUseGetters = true, callSuper = true)
public abstract class Node {
    /**
     * Тэг для логгирования.
     */
    private static String TAG = Node.class.getName();

    /**
     * Уникальный идентификатор ноды.
     */
    private String nodeId = null;

    /**
     * Блокируем создание полупустых классов.
     */
    protected Node() {
    }

    /**
     * Конструктор ноды.
     *
     * @param id уникальный идентификатор ноды
     */
    public Node(String id) {
        // проверка параметров
        checkId(id);
        // инициализация полей
        this.nodeId = id;
        // логгирование
        Log.d(TAG, "[cons] Node successfully created: " + this.toString());
    }

    /**
     * Получить идентификатор ноды.
     *
     * @return идентификатор ноды
     */
    public String getId() {
        Log.d(TAG, "[getId] The node: " + this.toString() + " has the id of " + this.nodeId + ", which is returned. ");
        return nodeId;
    }

    /**
     * Проверка корректности идентификатора.
     *
     * @param id идентификатор
     */
    private void checkId(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("The id of a node must be a non-empty string.");
        }
    }

}
