package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model;

import android.util.Log;

import lombok.ToString;

/**
 * Статичная нода — нода, содержащая текст, в том числе (опционально) переменные.
 */
@ToString(doNotUseGetters = true, callSuper = true)
public class StaticNode extends Node {

    /**
     * Тэг для логгирования.
     */
    private static final String TAG = StaticNode.class.getName();

    /**
     * Текстовое содержимое ноды.
     */
    private String content;

    /**
     * Идентификатор ноды, следующей за данной.
     */
    private String refId;

    /**
     * Блокируем создание полупустых классов.
     */
    private StaticNode() {
    }

    /**
     * Конструктор статичной ноды.
     *
     * @param id      идентификатор ноды
     * @param refId   идентификатор следующей ноды (может быть пустым)
     * @param content содержимое ноды
     */
    public StaticNode(String id, String refId, String content) {
        super(id);
        // проверка параметров
        checkContent(content);
        // инициализация класса
        this.refId = refId;
        this.content = content;
        // логгирование
        Log.d(TAG, "[cons] Static Node successfully created: " + this.toString());
    }

    /**
     * Получить текстовое содержимое ноды.
     *
     * @return текстовое содержимое ноды
     */
    public String getContent() {
        checkContent(this.content);
        Log.d(TAG, "[getContent] Getting the content of a node: " + this.toString());
        return content;
    }

    /**
     * Получить идентификатор следующей ноды.
     *
     * @return идентификатор следующей ноды
     */
    public String getRefId() {
        Log.d(TAG, "[getRefId] Getting the id of the next node of a node: " + this.toString());
        return refId;
    }

    /**
     * Является ли данная нода конечной в книге.
     * @return true, если нода конечная в книге, иначе — false
     */
    public boolean isFinal() {
        Log.d(TAG, "[isFinal] Checking if a node is final: " + this.toString());
        return refId == null || refId.isEmpty();
    }


    /**
     * Проверка содержимого ноды.
     *
     * @param content содержимое ноды
     */
    private void checkContent(String content) {
        if (content == null || content.isEmpty()) {
            Log.e(TAG, "[checkContent] The content of a static node must be a non-empty string.");
            throw new IllegalArgumentException("The content of a static node must be a non-empty string.");
        }
    }
}
