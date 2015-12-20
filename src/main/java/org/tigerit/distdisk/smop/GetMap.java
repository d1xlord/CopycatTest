package org.tigerit.distdisk.smop;

import io.atomix.copycat.client.Query;

/**
 * @author Nafis Ahmed
 */
public class GetMap implements Query<Object>{
    public GetMap() {
        System.out.println("Getting the whole map object");
    }
}
