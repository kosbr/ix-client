package ru.kos.ix.client;

import java.io.IOException;

/**
 * Created by Константин on 09.04.2016.
 */
public class RemoteCallException extends IOException {

    public RemoteCallException(final String message) {
        super(message);
    }

    public RemoteCallException(final Throwable cause) {
        super(cause);
    }
}
