package org.tigerit.distdisk.client;

import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.NettyTransport;
import io.atomix.copycat.client.CopycatClient;
import org.tigerit.distdisk.smop.GetCounter;
import org.tigerit.distdisk.smop.GetQuery;
import org.tigerit.distdisk.smop.IncrementCounter;
import org.tigerit.distdisk.smop.PutCommand;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

/**
 * @author Nafis Ahmed
 */
public class MainClient {
    public static void main(String[] args) {
        Collection<Address> members = Arrays.asList(
                new Address("localhost", 5000)
        );

        CopycatClient client = CopycatClient.builder(members)
                .withTransport(new NettyTransport())
                .build();

        System.out.println("Client starting");
        try {
            client.open().join();
            System.out.println("Client started");

            client.submit(new IncrementCounter()).get();
            System.out.println("Counter Incremented");

            client.submit(new GetCounter()).thenAccept(result -> {
                System.out.println("Value of Counter is: " + result);
            });


        } catch (Exception e) {
            System.out.println("Server not found!!");
        }
//        client.submit(new PutCommand("foo", "Hello World!!")).thenAccept(result -> {
//                    System.out.println("State Machine output: " + result);
//        });
//
//        long startTime = System.currentTimeMillis();
//        client.submit(new GetQuery("foo")).thenAccept(result -> {
//            System.out.println("Got: " + result);
//            long endTime = System.currentTimeMillis();
//            System.out.println("total time: " + (endTime - startTime));
//        });
    }
}
