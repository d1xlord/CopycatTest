package org.tigerit.distdisk.server;

import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.NettyTransport;
import io.atomix.copycat.server.CopycatServer;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Nafis Ahmed
 */
public class MainServer {
    
    public static void main(String args[]){
        Address address = new Address("localhost", 5000);
        Collection<Address> members = Arrays.asList(
            // new Address("localhost", 5000)
        );

        CopycatServer server = CopycatServer.builder(address, members)
                .withTransport(new NettyTransport())
                .withStateMachine(new MapStateMachine())
                .withStorage(new Storage("logs", StorageLevel.DISK))
                .build();

        server.open().thenRun(() -> System.out.println("Server started successfully!"));
    }
}
