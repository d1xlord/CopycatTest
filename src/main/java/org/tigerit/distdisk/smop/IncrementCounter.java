package org.tigerit.distdisk.smop;

import io.atomix.copycat.client.Command;

/**
 * @author Nafis Ahmed
 */
public class IncrementCounter implements Command<Object> {

    public IncrementCounter() {
        System.out.println("Increment Counter");
    }
}
