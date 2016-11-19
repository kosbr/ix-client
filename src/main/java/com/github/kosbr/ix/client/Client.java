package com.github.kosbr.ix.client;

import java.io.IOException;

/**
 * Created by kosbr on 09.04.2016.
 */
public interface Client {

    /**
     * Call remote service (synchronously)
     * @param serviceName name of service (service.[name] in server.properties of server application)
     * @param methodName name of method to call
     * @param args arguments
     * @return Result or null if method is void. (Result also can be null)
     * @throws RemoteCallException When there was some error on server side. (Service is not found, Method is not found,
     * Bad arguments or exception has been thrown during method running)
     * @throws IOException
     * @throws InterruptedException
     */
    Object remoteCall(String serviceName, String methodName, Object ... args) throws IOException, InterruptedException;

    /**
     * Closes socket
     * @throws IOException
     */
    void close() throws IOException;
}
