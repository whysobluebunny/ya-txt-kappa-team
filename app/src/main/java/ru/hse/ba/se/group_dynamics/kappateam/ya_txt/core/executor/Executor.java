package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.executor;

public abstract class Executor {

    protected Executor nextExecutor;

    public Executor(Executor nextExecutor) {
        this.nextExecutor = nextExecutor;
    }

    public abstract Object execute(String code);
}
