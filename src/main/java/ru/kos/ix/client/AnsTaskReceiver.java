package ru.kos.ix.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.kos.ix.dto.AnsTask;

import java.io.ObjectInputStream;
import java.net.SocketException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread which is waiting for {@link AnsTask} object in inputStream and puts in into a storage. <br/>
 * After saving income object in storage it calls notify method of corresponding monitor to allow other thread to get AnsTask from storage. <br/>
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
        logger.debug("Start AnsTaskReceiver");

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
                logger.warn("Stop AnsTaskReceiver. Socket is closed");
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException();
            }

    }

    /**
     * Sets monitor, which's method notify will be called after corresponding to taskId answer comes <br/>
     * It is possible to get nonnull ansewer using {@link #get(Integer)} method after notify call <br/>
     * @param taskId
     * @param monitor
     */
    public void notifyWhenDataWillBeReady(Integer taskId, Object monitor) {
        monitors.put(taskId, monitor);
    }

    /**
     * Returns answer for taskId. <br/>
     * If answer hasn't come yet, it returns null.
     * @param taskId
     * @return
     */
    public AnsTask get(Integer taskId) {
        return ansTaskMap.get(taskId);
    }

    /**
     * Removes answer from storage. <br/>
     * It is recommended to call it after success using of {@link #get(Integer)} method
     * @param taskId
     * @return
     */
    public AnsTask remove(Integer taskId) {
        return ansTaskMap.remove(taskId);
    }
}
