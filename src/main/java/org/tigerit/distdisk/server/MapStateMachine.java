package org.tigerit.distdisk.server;

import io.atomix.copycat.server.Commit;
import io.atomix.copycat.server.StateMachine;
import io.atomix.copycat.server.StateMachineExecutor;
import org.tigerit.distdisk.smop.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Nafis Ahmed
 */
public class MapStateMachine extends StateMachine {
    private final Map<Object, Commit<PutCommand>> map = new HashMap<>();
    private final AtomicInteger counter = new AtomicInteger();
    private Commit<IncrementCounter> prevIncrement = null;

    @Override
    protected void configure(StateMachineExecutor executor) {
        executor.register(PutCommand.class, this::put);
        executor.register(GetQuery.class, this::get);
        executor.register(RemoveCommand.class, this::remove);
        executor.register(IncrementCounter.class, this::incrementCounter);
        executor.register(GetCounter.class, this::getCounter);
    }

    private Object put(Commit<PutCommand> commit) {
        System.out.println("code is here");
        map.put(commit.operation().key(), commit);
        return commit.operation().key();
    }

    private Object get(Commit<GetQuery> commit) {
        try {
            System.out.println("Code is here");
            Commit<PutCommand> value = map.get(commit.operation().key());
            return value != null ? value.operation().value() : null;
        } finally {
            System.out.println("get is ending");
            commit.close();
        }
    }

    private Object remove(Commit<RemoveCommand> commit) {
        try {
            Commit<PutCommand> value = map.remove(commit.operation().key());

            if (value != null) {
                Object result = value.operation().value();
                value.clean();
                return result;
            }
            return null;
        } finally {
            commit.clean();
        }
    }

    private Object incrementCounter(Commit<IncrementCounter> commit) {
        Integer ret = counter.incrementAndGet();
        if(prevIncrement != null)
            prevIncrement.clean();
        prevIncrement = commit;
        return ret;
    }

    private Object getCounter(Commit<GetCounter> commit) {
        try {
            Integer value = counter.get();
            return value;
        }
        finally {
            commit.close();
        }
    }
}
