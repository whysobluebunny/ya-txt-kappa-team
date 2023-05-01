package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.interfaces;

/**
 * Интерфейс, абстрагирующий методы View, который умеет работать с HTML.
 */
public interface IHtmlView {

     /**
     * Загрузить HTML код в тег Body (с заменой)
     */
    void loadHtml(String html);

    /**
     * Заменить триггерную ноду новым содержимым.
     * @param nodeId идентификатор триггерной ноды
     * @param html новое содержимое
     */
    void replaceTriggerNodeBlock(String nodeId, String html);

    /**
     * Заменить исполняемую ноду новым содержимым
     * @param nodeId id исполняемой ноды
     * @param html новое содержимое
     */
    void replaceExecutableNodeBlock(String nodeId, String html);

    /**
     * Заменить переменную вычисленным значением.
     * @param varId идентификатор переменной
     * @param html вычисленное значение
     */
    void replaceVariableBlock(String varId, String html);

    /**
     * Сообщить View о том, что на бекенде все сломалось. :(
     */
    void reportErrorState();
}
