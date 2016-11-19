package ru.kos.ix.main;

import ru.kos.ix.client.Client;
import ru.kos.ix.client.ClientImpl;

/**
 * Created by Константин on 09.04.2016.
 */
public class OneClientThread extends Thread {

    private static final int PORT = 3128;
    private static final String HOST = "127.0.1.1";

    @Override
    public void run() {
        try {
            Client client = new ClientImpl(HOST, PORT);

            Runnable task1 = () -> {
                System.out.println(remoteCallSafe(client, "some", "toUpper", "my string"));
            };
            Runnable task2 = () -> {
                System.out.println(remoteCallSafe(client, "some", "toUpper", "my string"));
            };
            Runnable task3 = () -> {
                System.out.println(remoteCallSafe(client, "some", "plus", 1, 2));
            };
            Runnable task4 = () -> {
                System.out.println(remoteCallSafe(client, "some", "sleep", 5000));
            };
            Runnable task5 = () -> {
                System.out.println(remoteCallSafe(client, "some", "error"));
            };
            Runnable task6 = () -> {
                System.out.println(remoteCallSafe(client, "onename", "method", "lenta"));
            };
            Runnable task7 = () -> {
                System.out.println(remoteCallSafe(client, "onename", "method", "lenta", null));
            };
            Runnable task8 = () -> {
                System.out.println(remoteCallSafe(client, "onename", "method", "lenta", "metro", "ikea"));
            };
            Runnable task9 = () -> {
                System.out.println(remoteCallSafe(client, "onename", "method", "lenta", "metro", 12));
            };
            Runnable task10 = () -> {
                System.out.println(remoteCallSafe(client, "onename", "method", "lenta", "metro", null));
            };

            Thread thread1 = new Thread(task1);
            Thread thread2 = new Thread(task2);
            Thread thread3 = new Thread(task3);
            Thread thread4 = new Thread(task4);
            Thread thread5 = new Thread(task5);
            Thread thread6 = new Thread(task6);
            Thread thread7 = new Thread(task7);
            Thread thread8 = new Thread(task8);
            Thread thread9 = new Thread(task9);
            Thread thread10 = new Thread(task10);

            thread1.start();
            thread2.start();
            thread3.start();
            thread4.start();
            thread5.start();
            thread6.start();
            thread7.start();
            thread8.start();
            thread9.start();
            thread10.start();

            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
            thread5.join();
            thread6.join();
            thread7.join();
            thread8.join();
            thread9.join();
            thread10.join();

            client.close();

        } catch (Exception e) {
            System.out.println(e);
        }
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
