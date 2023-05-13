package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.HashMap;
import java.util.Optional;

import lombok.ToString;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.interfaces.IBookContentsProvider;

/**
 * Книга — неизменяемая READ-ONLY структура, хранящая содержимое книги, написанной писателем.
 */
@ToString(doNotUseGetters = true, callSuper = true)
public class Book implements IBookContentsProvider {

    /**
     * Тэг для логгирования.
     */
    private static final String TAG = Book.class.getName();

    /**
     * Уникальный идентификатор книги.
     */
    private String bookId;

    /**
     * Корневая нода книги (то есть самая первая нода в книге).
     */
    private Node rootNode;

    /**
     * Словарь всех нод в книге (ключ — идентификатор ноды, значение — нода, соответствующая ключу).
     */
    private HashMap<String, Node> nodes;

    /**
     * Словарь всех переменных в книге (ключ — идентификатор переменной, значение — переменная, соответствующая ключу).
     */
    private HashMap<String, Variable> variables;

    /**
     * Конструктор книги.
     *
     * @param bookId
     * @param rootNode  корневая нода книги
     * @param nodes     словарь всех нод книги
     * @param variables словарь всех переменных книги
     */
    public Book(String bookId, Node rootNode, HashMap<String, Node> nodes, HashMap<String, Variable> variables) {
        // проверки
        checkVariables(variables);
        checkNodes(nodes);
        // инициализация полей
        this.bookId = bookId;
        this.rootNode = rootNode;
        this.nodes = nodes;
        this.variables = variables;
        // логгирование
        Log.d(TAG, "[cons] Book successfully created.");
    }

    /**
     * Получить идентификатор книги.
     *
     * @return идентификатор книги
     */
    @Override
    public String getBookId() {
        checkId(bookId);

        Log.d(TAG, "[getBookId] The book has the id of value " + this.bookId + ", which is returned.");
        return bookId;
    }

    /**
     * Получить корневую ноду (то есть самую первую ноду в книге).
     *
     * @return корневая нода книги
     */
    @Override
    public Node getRootNode() {
        if (this.rootNode == null) {
            Log.e(TAG, "[getRootNode] The book's root node is not instantiated.");
            throw new IllegalStateException("The book's root node is not instantiated when needed to be provided.");
        }

        Log.d(TAG, "[getRootNode] The book has the root node " + this.rootNode.toString() + ", which is returned.");
        return this.rootNode;
    }

    /**
     * Получить ноду по ее идентификатору.
     *
     * @param id идентификатор
     * @return нода
     */
    @Override
    public Node getNodeById(String id) {
        // проверки
        checkNodes(this.nodes);
        checkId(id);

        // поиск
        Node node = nodes.get(id);
        if (node == null) {
            Log.e(TAG, "[getNodeById] The book doesn't contain a node with the id value of " + id);
            throw new IllegalArgumentException("The book doesn't contain a node with the id value of " + id);
        }

        Log.d(TAG, "[getNodeById] The book has the node with the provided id " + node.toString() + ", which is returned.");
        return node;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Optional<Node> findNodeById(String id) {
        checkNodes(this.nodes);
        checkId(id);
        return Optional.ofNullable(this.nodes.get(id));
    }

    /**
     * Получить переменную по ее идентификатору.
     *
     * @param id идентификатор
     * @return переменная
     */
    @Override
    public Variable getVarById(String id) {
        // проверки
        checkVariables(this.variables);
        // поиск
        Variable variable = variables.get(id);
        if (variable == null) {
            Log.e(TAG, "[getVarById] The book doesn't contain a variable with the id value of " + id);
            throw new IllegalArgumentException("The book doesn't contain a variables with the id value of " + id);
        }

        Log.d(TAG, "[getVarById] The book has the variable with the provided id " + variable.toString() + ", which is returned.");
        return variable;
    }

    /**
     * Проверка идентификатора.
     *
     * @param id идентификатор
     */
    private void checkId(String id) {
        if (id == null || id.isEmpty()) {
            Log.e(TAG, "[checkId] The id of a book must be a non-empty string.");
            throw new IllegalArgumentException("The id of a book must be a non-empty string.");
        }
    }

    /**
     * Проверка нод.
     *
     * @param nodes ноды
     */
    private void checkNodes(HashMap<String, Node> nodes) {
        if (nodes == null) {
            Log.e(TAG, "[checkNodes] The book's nodes must be instantiated.");
            throw new IllegalStateException("The book's nodes must be instantiated.");
        }
        if (nodes.isEmpty()) {
            Log.e(TAG, "[checkNodes] The book must have at least one node.");
            throw new IllegalStateException("The book must have at least one node.");
        }
    }

    /**
     * Проверка переменных.
     *
     * @param variables переменные
     */
    private void checkVariables(HashMap<String, Variable> variables) {
        if (variables == null) {
            Log.e(TAG, "[checkVariables] The book's variables must be instantiated.");
            throw new IllegalStateException("The book's variables must be instantiated.");
        }
    }
}
