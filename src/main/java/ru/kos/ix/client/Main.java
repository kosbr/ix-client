package ru.kos.ix.client;

import ru.kos.ix.dto.AnsTask;
import ru.kos.ix.dto.Task;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * Created by Константин on 08.04.2016.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost", 3129);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(s.getOutputStream());
        ObjectInputStream objectInputStream = new ObjectInputStream(s.getInputStream());

        Task task = new Task();
        Object[] arguments = new Object[1];
        arguments[0] = "Some string";
        task.setArguments(arguments);
        task.setId(1);
        task.setMethodName("toUpper");
        task.setServiceName("some");


        objectOutputStream.writeObject(task);
        objectOutputStream.flush();

        AnsTask data = (AnsTask)objectInputStream.readObject();
        System.out.println("data=" + data);

        s.close();
    }
}
