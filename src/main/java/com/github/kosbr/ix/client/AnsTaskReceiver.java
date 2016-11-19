package com.github.kosbr.ix.client;

import com.github.kosbr.ix.dto.AnsTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ObjectInputStream;
import java.net.SocketException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread which is waiting for {@link AnsTask} object in inputStream and puts in into a storage. <br/>
 * After saving income object in storage it calls notify method of corresponding monitor to allow other
 * thread to get AnsTask from storage. <br/>
 * Created by Константин on 09.04.2016.
 */
public class AnsTaskReceiver extends Thread {

    private static final Logger LOGGER = LogManager.getLogger(AnsTaskReceiver.class);

    private Map<Integer, AnsTask> ansTaskMap = new ConcurrentHashMap<>();
    private Map<Integer, Object> monitors = new ConcurrentHashMap<>();
    private ObjectInputStream objectInputStream;

    public AnsTaskReceiver(final ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }

    @Override
    public void run() {
        LOGGER.debug("Start AnsTaskReceiver");

            try {
                while (true) {
                    AnsTask ansTask = (AnsTask) objectInputStream.readObject();
                    LOGGER.info("Ans has come: " + ansTask);
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
                LOGGER.warn("Stop AnsTaskReceiver. Socket is closed");
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                throw new RuntimeException();
            }

    }

    /**
     * Sets monitor, which's method notify will be called after corresponding to taskId answer comes <br/>
     * It is possible to get nonnull ansewer using {@link #get(Integer)} method after notify call <br/>
     * @param taskId
     * @param monitor
     */
    public void notifyWhenDataWillBeReady(final Integer taskId, final Object monitor) {
        monitors.put(taskId, monitor);
    }

    /**
     * Returns answer for taskId. <br/>
     * If answer hasn't come yet, it returns null.
     * @param taskId
     * @return
     */
    public AnsTask get(final Integer taskId) {
        return ansTaskMap.get(taskId);
    }

    /**
     * Removes answer from storage. <br/>
     * It is recommended to call it after success using of {@link #get(Integer)} method
     * @param taskId
     * @return
     */
    public AnsTask remove(final Integer taskId) {
        return ansTaskMap.remove(taskId);
    }
}
