package org.tigerit.distdisk.smop;

import io.atomix.copycat.client.Query;

/**
 * @author Nafis Ahmed
 */
public class GetQuery implements Query<Object> {
    private final Object key;

    public GetQuery(Object key) {
        this.key = key;
    }

    public Object key() {
        return key;
    }
}
