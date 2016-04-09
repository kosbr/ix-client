package ru.kos.ix.client;

/**
 * Created by Константин on 08.04.2016.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Client client = new ClientImpl();
        System.out.println(remoteCallSafe(client, "some", "toUpper", "my string"));
        System.out.println(remoteCallSafe(client, "some", "plus", 1, 2));
        System.out.println(remoteCallSafe(client, "some", "sleep", 5000));
        System.out.println(remoteCallSafe(client, "onename", "method", "lenta"));
        System.out.println(remoteCallSafe(client, "onename", "method", "lenta", "metro", "ikea"));
        System.out.println(remoteCallSafe(client, "onename", "method", "lenta", "metro", 12));
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
