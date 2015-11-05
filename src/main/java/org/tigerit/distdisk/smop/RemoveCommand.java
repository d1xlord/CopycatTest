package org.tigerit.distdisk.smop;

import io.atomix.copycat.client.Command;

/**
 * @author Nafis Ahmed
 */
public class RemoveCommand implements Command<Object> {
    private final Object key;

    public RemoveCommand(Object key) {
        this.key = key;
    }

    @Override
    public PersistenceLevel persistence() {
        return PersistenceLevel.PERSISTENT;
    }

    public Object key() {
        return key;
    }
}

