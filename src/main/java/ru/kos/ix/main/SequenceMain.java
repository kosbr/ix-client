package ru.kos.ix.main;

import ru.kos.ix.client.Client;
import ru.kos.ix.client.ClientImpl;

/**
 * Created by Константин on 08.04.2016.
 */
public class SequenceMain {
    public static void main(String[] args) throws Exception {
        Client client = new ClientImpl("localhost", 3128);
        System.out.println(remoteCallSafe(client, "some", "toUpper", "my string"));
        System.out.println(remoteCallSafe(client, "unexist", "toUpper", "my string"));
        System.out.println(remoteCallSafe(client, "some", "plus", 1, 2));
        System.out.println(remoteCallSafe(client, "some", "sleep", 5000)); //long time
        System.out.println(remoteCallSafe(client, "some", "error")); //npe
        System.out.println(remoteCallSafe(client, "onename", "method", "lenta")); //no such method
        System.out.println(remoteCallSafe(client, "onename", "method", "lenta", null));
        System.out.println(remoteCallSafe(client, "onename", "method", "lenta", "metro", "ikea"));
        System.out.println(remoteCallSafe(client, "onename", "method", "lenta", "metro", 12));
        System.out.println(remoteCallSafe(client, "onename", "method", "lenta", "metro", null)); //two methods
        client.close();
    }

    public static Object remoteCallSafe(Client client, String serviceName, String methodName, Object ... args) {
        try {
            return client.remoteCall(serviceName, methodName, args);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
