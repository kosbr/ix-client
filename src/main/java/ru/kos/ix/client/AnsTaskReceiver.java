package ru.kos.ix.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.kos.ix.dto.AnsTask;

import java.io.ObjectInputStream;
import java.net.SocketException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Константин on 09.04.2016.
 */
public class AnsTaskReceiver extends Thread {

    private static final Logger logger = LogManager.getLogger(AnsTaskReceiver.class);

    private Map<Integer, AnsTask> ansTaskMap = new ConcurrentHashMap<>();
    private Map<Integer, Object> monitors = new ConcurrentHashMap<>();
    private ObjectInputStream objectInputStream;

    public AnsTaskReceiver(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }

    @Override
    public void run() {
        logger.info("Start AnsTaskReceiver");

            try {
                while (true) {
                    AnsTask ansTask = (AnsTask) objectInputStream.readObject();
                    logger.info("Ans has come: " + ansTask);
                    ansTaskMap.put(ansTask.getId(), ansTask);
                    Object monitor = monitors.get(ansTask.getId());
                    if (monitor != null) {
                        synchronized (monitor) {
                            monitor.notify();
                        }
                    }
                    monitors.remove(ansTask.getId());
                }
            } catch (SocketException e) {
                logger.info("Stop AnsTaskReceiver. Socket is closed");
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException();
            }

    }

    public void notifyWhenDataWillBeReady(Integer taskId, Object monitor) {
        monitors.put(taskId, monitor);
    }

    public AnsTask get(Integer taskId) {
        return ansTaskMap.get(taskId);
    }

    public AnsTask remove(Integer taskId) {
        return ansTaskMap.remove(taskId);
    }
}
