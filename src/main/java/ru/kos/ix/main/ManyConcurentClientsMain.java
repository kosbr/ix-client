package ru.kos.ix.main;

/**
 * Created by Константин on 09.04.2016.
 */
public class ManyConcurentClientsMain {
    public static void main(String[] args) {
        OneClientThread client1 = new OneClientThread();
        OneClientThread client2 = new OneClientThread();
        OneClientThread client3 = new OneClientThread();
        OneClientThread client4 = new OneClientThread();

        client1.start();
        client2.start();
        client3.start();
        client4.start();
    }
}
