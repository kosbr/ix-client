package ru.kos.ix.main;

import ru.kos.ix.client.Client;
import ru.kos.ix.client.ClientImpl;

/**
 * Created by Константин on 08.04.2016.
 */
public final class SequenceMain {

    private static final int PORT = 3128;
    private static final String HOST = "127.0.1.1";

    private SequenceMain() {
    }

    /**
     * This is test main method. <br/>
     * It creates one client, connects to server and sends successively different requests to server. <br/>
     * Because of remoteCall method in {@link Client} is thread blocking, next request won't be sent before
     * previous answer is waiting.
     * @param args
     * @throws Exception
     */
    public static void main(final String[] args) throws Exception {
        Client client = new ClientImpl(HOST, PORT);
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

    public static Object remoteCallSafe(final Client client, final String serviceName,
                                        final String methodName, final Object ... args) {
        try {
            return client.remoteCall(serviceName, methodName, args);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
