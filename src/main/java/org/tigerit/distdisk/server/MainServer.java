package org.tigerit.distdisk.server;

import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.NettyTransport;
import io.atomix.copycat.server.CopycatServer;
import io.atomix.copycat.server.RaftServer;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

/**
 * @author Nafis Ahmed
 */
public class MainServer {
    
    public static void main(String args[]){
        int portNum = 5000;
        if(args.length > 0) {
            portNum = Integer.parseInt(args[0]);
        } else {
            System.out.println("Pass port number as argument");
        }
        Address address = new Address("localhost", portNum);
        Collection<Address> members = Arrays.asList(
            new Address("localhost", 5000),
            new Address("localhost", 5001),
            new Address("localhost", 5002)
        );

        CopycatServer server = CopycatServer.builder(address, members)
                .withTransport(new NettyTransport())
                .withStateMachine(new MapStateMachine())
                .withStorage(new Storage("logs", StorageLevel.DISK))
                .build();

        System.out.println("Starting Server at port: " + portNum + "...");
        server.open().join();
        System.out.println("Server started successfully!");
        System.out.println("Leader: " + server.leader());


        server.onLeaderElection(leader -> {
            System.out.println("*** leader changing ***");
            System.out.println("New leader: " + server.leader());
        });
    }
}
