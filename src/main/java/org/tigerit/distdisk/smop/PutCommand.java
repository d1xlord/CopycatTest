package org.tigerit.distdisk.smop;

import io.atomix.copycat.client.Command;

/**
 * @author Nafis Ahmed
 */
public class PutCommand implements Command<Object> {
    private final Object key;
    private final Object value;

    public PutCommand(Object key, Object value) {
        this.key = key;
        this.value = value;
    }

    public Object key() {
        return key;
    }

    public Object value() {
        return value;
    }
}
