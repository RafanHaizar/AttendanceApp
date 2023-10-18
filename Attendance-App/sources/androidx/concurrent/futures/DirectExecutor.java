package androidx.concurrent.futures;

import java.util.concurrent.Executor;

enum DirectExecutor implements Executor {
    INSTANCE;

    public void execute(Runnable command) {
        command.run();
    }

    public String toString() {
        return "DirectExecutor";
    }
}
