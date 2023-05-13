package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.executor;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.function.Predicate;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JavaScriptExecutor extends Executor {

    public JavaScriptExecutor(Executor nextExecutor) {
        super(nextExecutor);
    }

    @Override
    public String execute(String code) {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        try {
            return (String) engine.eval(code);

        } catch (ScriptException ex) {
            if (nextExecutor != null)
                return nextExecutor.execute(code);
        }
        return null;
    }
}
