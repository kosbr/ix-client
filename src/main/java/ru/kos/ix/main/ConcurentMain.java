package ru.kos.ix.main;


/**
 * Created by Константин on 09.04.2016.
 */
public class ConcurentMain {
    public static void main(String[] args) throws Exception {
        OneClientThread oneClientThread = new OneClientThread();
        oneClientThread.start();
    }
}
