package ru.kos.ix.client;


/**
 * Created by Константин on 08.04.2016.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Client client = new ClientImpl();
        System.out.println(client.remoteCall("some", "toUpper", "my string"));
        System.out.println(client.remoteCall("some", "plus", 1, 2));
        System.out.println(client.remoteCall("some", "sleep", 5000));
        System.out.println(client.remoteCall("onename", "method", "lenta"));
        System.out.println(client.remoteCall("onename", "method", "lenta", "metro", "ikea"));
        System.out.println(client.remoteCall("onename", "method", "lenta", "metro", 12));
        client.close();
    }
}
