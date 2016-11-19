package com.github.kosbr.ix.main;

/**
 * Created by kosbr on 09.04.2016.
 */
public final class ManyConcurrentClientsMain {

    private ManyConcurrentClientsMain() {
    }

    /**
     * This is test main method. <br/>
     * It creates four clients, connects them to server and sends in parallel different
     * requests to server from every client in parallel. <br/>
     * The server must handle it in parallel and return every answer when it is ready to corresponding client <br/>
     * Also client application must show answers in order of coming. (not sending)
     * @param args
     * @throws Exception
     */
    public static void main(final String[] args) {
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
