package org.tigerit.distdisk.smop;

import io.atomix.copycat.client.Query;

/**
 * @author Nafis Ahmed
 */
public class GetCounter implements Query<Object>{

    public GetCounter() {
        System.out.println("Get Counter");
    }
}
