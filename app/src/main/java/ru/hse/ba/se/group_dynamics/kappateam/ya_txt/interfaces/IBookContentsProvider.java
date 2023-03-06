package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.interfaces;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Node;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Variable;

/**
 * Интерфейс абстрагирует класс, который способен предоставлять элементы книги.
 */
public interface IBookContentsProvider {

    /**
     * Получить идентификатор книги.
     *
     * @return идентификатор книги
     */
    String getBookId();

    /**
     * Получить корневую ноду (то есть самую первую ноду в книге).
     *
     * @return корневая нода книги
     */
    Node getRootNode();

    /**
     * Получить ноду по ее идентификатору.
     * @return нода
     */
    Node getNodeById(String id);

    /**
     * Получить переменную по ее идентификатору.
     * @return переменная
     */
    Variable getVarById(String id);

}
