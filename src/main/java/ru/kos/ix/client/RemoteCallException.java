package ru.kos.ix.client;

import java.io.IOException;

/**
 * Created by Константин on 09.04.2016.
 */
public class RemoteCallException extends IOException {

    public RemoteCallException(String message) {
        super(message);
    }

    public RemoteCallException(Throwable cause) {
        super(cause);
    }
}
