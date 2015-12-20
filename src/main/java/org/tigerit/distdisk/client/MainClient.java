package org.tigerit.distdisk.client;

import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.NettyTransport;
import io.atomix.copycat.client.CopycatClient;
import org.tigerit.distdisk.smop.*;

import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * @author Nafis Ahmed
 */
public class MainClient {
    public static void main(String[] args) {
        int masterPort = Integer.parseInt(args[0]);
        Collection<Address> members = Arrays.asList(
                new Address("localhost", masterPort)
        );

        CopycatClient client = CopycatClient.builder(members)
                .withTransport(new NettyTransport())
                .build();

        System.out.println("Client starting at port: " + masterPort);

        try {
            client.open().join();
            System.out.println("Client started");

            Scanner in = new Scanner(System.in);
            int count=1;
            while (true) {
                int now = in.nextInt();
                if (now == 0) { // Getting Counter Value
                    client.submit(new IncrementCounter()).get();
                    System.out.println("Counter Incremented");

                    long startTime = System.currentTimeMillis();
                    Object obj = client.submit(new GetCounter()).get();
                    long endTime = System.currentTimeMillis();
                    System.out.println("total time: " + (endTime - startTime) + "ms");

                    String ret = obj.toString();
                    System.out.println("Value of Counter is: " + ret);

                } else if( (now >= 1) && (now <= 4)) { // Getting the whole map
                    switch (now){
                        case 1:
                            client.submit(new PutCommand("foo", "Command " + count++)).get();
                            break;
                        case 2:
                            client.submit(new PutCommand("bar", "Command " + count++)).get();
                            break;
                        case 3:
                            client.submit(new PutCommand("baz", "Command " + count++)).get();
                            break;
                        default:
                            client.submit(new PutCommand("bang", "Command " + count++)).get();
                    }

                    long startTime = System.currentTimeMillis();
                    Object obj = client.submit(new GetMap()).get();
                    long endTime = System.currentTimeMillis();
                    System.out.println("total time: " + (endTime - startTime));

                    Map<String, String> hm = (HashMap<String , String>) obj;

                    System.out.println("Map -->");
                    for(Map.Entry<String , String > entry : hm.entrySet()) {
                        String key = entry.getKey();
                        String val = entry.getValue();
                        System.out.println(key + ":" + val);
                    }
                } else break;
            }
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
