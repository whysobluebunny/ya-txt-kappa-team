package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.engines;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.ExecutableNode;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Node;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.StaticNode;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Trigger;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.TriggerNode;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Variable;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.BookRepository;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.executor.Executor;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.engines.trigger_engines.TriggerEngine;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.engines.variable_engines.VariableEngine;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.executor.NonExecutableCodeException;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.interfaces.IBookContentsProvider;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.interfaces.IHtmlView;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.interfaces.IHtmlViewEventsHandler;

/**
 * Класс-движок, реализующий динамическую логику книги.
 */
public class BookEngine implements IHtmlViewEventsHandler {

    /**
     * Обьект для генерации случайных чисел
     */
    private static final Random _random = new Random();

    /**
     * Тэг для логирования
     */
    private final String TAG = "BookEngine";

    /**
     * Контекст приложения
     */
    private final Context _context;

    /**
     * Контент провайдер книги
     */
    private final IBookContentsProvider _contentsProvider;

    private final Executor nodeExecutor;

    /**
     * Конструктор класса
     *
     * @param context          контекст приложения
     * @param contentsProvider провайдер контента книги
     */
    public BookEngine(Context context,
                      IBookContentsProvider contentsProvider,
                      Executor nodeExecutor) throws IllegalArgumentException {
        if (context == null || contentsProvider == null)
            Log.e(TAG, "[cons] Argument can't be null.", new IllegalArgumentException("Argument can't be null"));

        this._context = context;
        this._contentsProvider = contentsProvider;
        this.nodeExecutor = nodeExecutor;
    }

    /**
     * Обработчик события открытия книги
     * @param callbackObject обьект показа HTML кода
     * @param bookId идентификатор книги
     */
    @Override
    public void onBookOpen(IHtmlView callbackObject, String bookId) {
        final String functionName = "[onBookOpen]";
        try {
            if (BookRepository.isCurrentBookProgressEmpty(bookId, _context)) {
                String content = newDivBlock("scroll-pos-meta-block", null, "") +
                                 loadNotes(_contentsProvider.getRootNode());
                callbackObject.loadHtml(content);
            } else {
                callbackObject.loadHtml(BookRepository.getCurrentBookProgress(bookId, _context));
            }
            Log.i(TAG, functionName+ " Book was loaded successfully.");
        }
        catch (Exception e) {
            Log.e(TAG, functionName + " Error occurred while opening the book.", e);
            callbackObject.reportErrorState();
        }
    }

    /**
     * Обработчик события закрытия книги
     * @param bookId идентификатор книги
     * @param htmlContent текущее содержимое книги
     */
    @Override
    public void onBookClose(String bookId, String htmlContent, IHtmlView callbackObject) {
        final String functionName = "[onBookClose]";
        try {
            BookRepository.saveCurrentBookProgress(bookId, htmlContent, _context);
            Log.i(TAG, functionName + " Book was closed successfully.");
        } catch (IOException e) {
            Log.e(TAG, functionName + " Error occurred while closing the book.", e);
            callbackObject.reportErrorState();
        }
    }

    /**
     * Обработчик события достижения (появления на экране) триггерной ноды
     * @param callbackObject обьект показа HTML кода
     * @param nodeId ID триггерной ноды
     */
    @Override
    public void onTriggerNodeAppearance(IHtmlView callbackObject, String nodeId){
        final String functionName = "[onTriggerNodeAppearance]";
        try {
            TriggerNode triggerNode = (TriggerNode) _contentsProvider.getNodeById(nodeId);
            ArrayList<Node> nodes = new ArrayList<>();
            Node nextNode = null;
            String reason = ""; // причина выпадения текста
            // проверяем каждый из триггеров на истинность
            for (Trigger trigger : triggerNode.getTriggers()) {
                if (TriggerEngine.executeTrigger(trigger)) {
                    // если удалось обнаружить истинный - покидаем цикл
                    nextNode = _contentsProvider.getNodeById(trigger.getRefId());
                    try {
                        reason = trigger.getTriggerReason();
                    }
                    catch (Exception e) {
                        reason = "мистика";
                    }
                    break;
                }
                else {
                    // если истинный не найден, копим триггеры в массив для выбора случайного триггера
                    nodes.add(_contentsProvider.getNodeById(trigger.getRefId()));
                }
            }
            // если ни один из триггеров не выполнился, выбираем случайный
            if (nextNode == null) {
                nextNode = nodes.get(_random.nextInt(nodes.size()));
                reason = "воля случая";
                nodes.clear();
            }
            // заказываем у фронта замену триггерной ноды новым содержимым
            callbackObject.replaceTriggerNodeBlock(nodeId, newDivBlock("reason-block", null, reason) + loadNotes(nextNode));
            Log.i(TAG, functionName + " Trigger node was handled successfully.");
        }
        catch (Exception e) {
            Log.e(TAG, functionName + " Error occurred while handling the trigger node.", e);
            callbackObject.reportErrorState();
        }
    }

    @Override
    public void onExecutableNodeAppearance(IHtmlView callbackObject, String nodeId) {
        String funcName = "onExecutableNodeAppearance";
        try {
            ExecutableNode node = (ExecutableNode) _contentsProvider.getNodeById(nodeId);

            Object executed = nodeExecutor.execute(node.getCode());
            if (!(executed instanceof String)) {
                throw new NonExecutableCodeException();
            }
            String nextNodeId = (String) executed;
            Log.i(TAG, "[" + funcName + "] node id after execution=" + nextNodeId);
            Node nextNode = _contentsProvider.getNodeById(nextNodeId);

            callbackObject.replaceExecutableNodeBlock(nodeId, newDivBlock("reason-block", null, "выполнился код") + loadNotes(nextNode));

        } catch (Exception ex) {
            Log.e(TAG, "[" + funcName + "] Error occurred while handling the trigger node.", ex);
            callbackObject.reportErrorState();
        }
    }


    /**
     * Обработчик события достижения (появления на экране) переменной
     * @param callbackObject обьект показа HTML кода
     * @param varId ID переменной
     */
    @Override
    public void onVariableAppearance(IHtmlView callbackObject, String varId){
        final String functionName = "[onVariableAppearance]";
        try {
            Variable variable = _contentsProvider.getVarById(varId);
            // передаем задачу по вычислению значения переменной движку переменных
            callbackObject.replaceVariableBlock(varId, VariableEngine.getCalculatedContent(variable, _context));
            Log.i(TAG, functionName + " Variable value was injected successfully.");
        }
        catch (Exception e) {
            Log.e(TAG, functionName + " Error occurred while handling the variable node.", e);
            callbackObject.reportErrorState();
        }
    }

    /**
     * Ишет переменные в заданной строке и заменяет их соответствующим блоком
     *
     * @param content заданная строка
     * @return заданная строка, в которой переменные из формы $$$id$$$ переделаны в формат маркировочных структур (DIV-ов)
     */
    private String replaceVariablesInText(String content) {
        // Паттерн для нахождения полей переменных
        final String VARIABLE_BLOCK_PATTERN = "\\$\\$\\$(.*?)\\$\\$\\$";
        StringBuilder stringBuilder = new StringBuilder(content);
        Matcher m = Pattern.compile(VARIABLE_BLOCK_PATTERN).matcher(content);
        int offset = 0;
        while (m.find()) {
            String id = m.group();
            id = id.substring(3, id.length() - 3);
            int start = m.start();
            String newContent = newVariableDivBlock(id);
            stringBuilder.replace(offset + start, offset + start + id.length() + 6, newContent);
            offset += (newContent.length() - id.length() - 6);
        }
        return stringBuilder.toString();
    }

    /**
     * Достает статичные ноды, начиная с заданной корневой ноды, вплоть до
     * первой триггерной ноды или до достижения конца книги.
     * @param root корень
     */
    private String loadNotes(Node root) {
        final String functionName = "[loadNotes]";

        StringBuilder content = new StringBuilder();
        // загрузка статичных нод
        while (root instanceof StaticNode) {
            StaticNode staticNode = (StaticNode) root;
            String html = newStaticNodeDivBlock(staticNode.getId(), staticNode.getContent());
            content.append(html);
            try {
                root = _contentsProvider.getNodeById(staticNode.getRefId());
            }
            catch (Exception e) {
                // если достигли конца книги, то падает исключение, что ноды с таким ID нет
                root = null;
            }
        }
        // переходим к подгрузке триггерной ноды (одной!), если не настигли концовку
        if (root instanceof TriggerNode) {
            TriggerNode triggerNode = (TriggerNode) root;
            String html = newTriggerNodeDivBlock(triggerNode.getId());
            content.append(html);
        }
        // загрузка исполняемой ноды
        else if (root instanceof ExecutableNode) {
            ExecutableNode executableNode = (ExecutableNode) root;
            String html = newExecutableNodeDivBlock(executableNode.getId());
            content.append(html);
        }

        return content.toString();
    }

    //region HTML Utils

    /**
     * Создает и возврашает DIV блок
     *
     * @param divClass   класс блока
     * @param divID      ID блока
     * @param divContent контент внутри блока
     * @return DIV блок
     */
    private String newDivBlock(String divClass, String divID, String divContent) {
        return "<div" + " class=\"" + divClass + "\"" + (divID != null && !divID.isEmpty() ? "id=\"" + divID + "\"" : "" ) + ">" + divContent + "</div>";
    }

    /**
     * Создает и возврашает DIV блок для статических нод
     *
     * @param divID      ID ноды
     * @param divContent контент ноды
     * @return DIV блок для статических нод
     */
    private String newStaticNodeDivBlock(String divID, String divContent) {
        return newDivBlock("content_block", "", replaceVariablesInText(divContent));
    }

    /**
     * Создает и возвращает DIV блок для триггерной ноды
     *
     * @param divID ID ноды
     * @return DIV блок для триггерной ноды
     */
    private String newTriggerNodeDivBlock(String divID) {
        return newDivBlock("trigger_node_block", divID, "");
    }

    /**
     * Создает и возвращает DIV блок для исполняемой ноды
     * @param divID id ноды
     * @return DIV блок для исполняемой ноды
     */
    private String newExecutableNodeDivBlock(String divID) {
        return newDivBlock("executable_node_block", divID, "");
    }

    /**
     * Создает и возврашает DIV блок для переменных
     *
     * @param divID ID переменной
     * @return DIV блок для переменной
     */
    private String newVariableDivBlock(String divID) {
        return newDivBlock("variable_block", divID, "");
    }

    //endregion
}
