package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.ExecutableNode;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Node;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.StaticNode;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Variable;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.engines.BookEngine;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.executor.Executor;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.executor.JavaScriptExecutor;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.interfaces.IBookContentsProvider;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.interfaces.IHtmlView;

class BookEngineTest {

    @Test
    void test() {
        Map<String, Node> map = new HashMap<>();
        map.put("1", new ExecutableNode("1", "var a = '2'; a"));
        map.put("2", new StaticNode("2", null, "some content"));

        MockBook mockBook = new MockBook(map);

        Executor executor = new JavaScriptExecutor(null);

        final BookEngine bookEngine = new BookEngine(
            null,
                mockBook,
                executor
        );

        MockReadActivity mockReadActivity = new MockReadActivity();
        bookEngine.onExecutableNodeAppearance(mockReadActivity, "1");

        String html = mockReadActivity.getHtml("1");
        assertEquals("<div class=\"reason-block\">выполнился код</div><div class=\"content_block\">some content</div>",
                html);
    }

    static class MockReadActivity implements IHtmlView {

        private final Map<String, String> map;

        public MockReadActivity() {
            map = new HashMap<>();
        }

        @Override
        public void loadHtml(String html) {

        }

        @Override
        public void replaceTriggerNodeBlock(String nodeId, String html) {

        }

        @Override
        public void replaceExecutableNodeBlock(String nodeId, String html) {
            map.put(nodeId, html);
        }

        @Override
        public void replaceVariableBlock(String varId, String html) {

        }

        @Override
        public void reportErrorState() {

        }

        public String getHtml(String nodeId) {
            return map.get(nodeId);
        }
    }

    static class MockBook implements IBookContentsProvider {

        private final Map<String, Node> map;

        public MockBook(Map<String, Node> map) {
            this.map = map;
        }

        @Override
        public String getBookId() {
            return null;
        }

        @Override
        public Node getRootNode() {
            return null;
        }

        @Override
        public Node getNodeById(String id) {
            Node node = map.get(id);
            if (node == null) {
                throw new IllegalStateException();
            }
            return node;
        }

        @Override
        public Optional<Node> findNodeById(String id) {
            return Optional.ofNullable(map.get(id));
        }

        @Override
        public Variable getVarById(String id) {
            return null;
        }
    }

}