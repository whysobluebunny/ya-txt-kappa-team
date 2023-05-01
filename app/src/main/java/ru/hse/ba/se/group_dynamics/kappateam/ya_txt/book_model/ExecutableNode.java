package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model;

/**
 * Исполняемая нода содержит код, который после испольнения возвращает id следующей ноды
 */
public class ExecutableNode extends Node {

    private final String code;

    public ExecutableNode(String id, String code) {
        super(id);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
