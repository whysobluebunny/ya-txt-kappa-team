package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.executor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class JavaScriptExecutorTest {

    @Test
    void test() {
        Executor executor = new JavaScriptExecutor(null);

        String code =
                "var a = 1;" +
                "var b = 2;" +
                "parseInt(a + b)";
        Object res = executor.execute(code);
        assertEquals(3, res);
    }
}
