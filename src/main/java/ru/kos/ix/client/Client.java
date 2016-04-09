package ru.kos.ix.client;

import java.io.IOException;

/**
 * Created by Константин on 09.04.2016.
 */
public interface Client {

    Object remoteCall(String serviceName, String methodName, Object ... args) throws IOException, InterruptedException;

    void close() throws IOException;
}
