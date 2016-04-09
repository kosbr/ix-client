package ru.kos.ix.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.kos.ix.dto.AnsTask;
import ru.kos.ix.dto.Task;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Константин on 09.04.2016.
 */
public class ClientImpl implements Client {

    private static final Logger logger = LogManager.getLogger(ClientImpl.class);

    private final ObjectOutputStream objectOutputStream;
    private final AnsTaskReceiver ansTaskReceiver;
    private volatile AtomicInteger requestCounter;
    private final Socket s;

    public ClientImpl() throws IOException {
        logger.info("Client is started");
        s = new Socket("localhost", 3129);
        objectOutputStream = new ObjectOutputStream(s.getOutputStream());
        ObjectInputStream objectInputStream = new ObjectInputStream(s.getInputStream());
        ansTaskReceiver = new AnsTaskReceiver(objectInputStream);
        requestCounter = new AtomicInteger(0);
        ansTaskReceiver.start();
    }

    @Override
    public Object remoteCall(String serviceName, String methodName, Object ... args) throws IOException, InterruptedException {
        int requestId = requestCounter.incrementAndGet();
        Task task = new Task();
        task.setArguments(args);
        task.setId(requestId);
        task.setMethodName(methodName);
        task.setServiceName(serviceName);

        Object monitor = new Object();
        ansTaskReceiver.notifyWhenDataWillBeReady(requestId, monitor);

        synchronized (this) {
            logger.info("Send task: " + task);
            objectOutputStream.writeObject(task);
            objectOutputStream.flush();
        }

        synchronized (monitor) {
            if (ansTaskReceiver.get(requestId) == null) {
                logger.info("Waiting for answer: " + requestId);
                monitor.wait();
            }
        }

        AnsTask ansTask = ansTaskReceiver.get(requestId);
        ansTaskReceiver.remove(requestId);
        logger.info("Ready to return ans: " + ansTask);
        //todo statuses
        return ansTask.getResult();
    }

    @Override
    public void close() throws IOException {
        s.close();
    }
}
