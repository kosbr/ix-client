package ru.kos.ix.client;

import ru.kos.ix.dto.Task;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Константин on 08.04.2016.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost", 3129);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(s.getOutputStream());
        ObjectInputStream objectInputStream = new ObjectInputStream(s.getInputStream());

        List<String> stringList = new ArrayList<String>();
        stringList.add("Hello");
        stringList.add("World");

        Task task = new Task();
        Object[] arguments = new Object[1];
        arguments[0] = stringList;
        task.setArguments(arguments);
        task.setId(1);
        task.setMethodName("method1");
        task.setServiceName("service");


        objectOutputStream.writeObject(task);
        objectOutputStream.flush();

        Task task2 = new Task();
        Object[] arguments2 = new Object[1];
        arguments[0] = stringList;
        task2.setArguments(arguments2);
        task2.setId(2);
        task2.setMethodName("method2");
        task2.setServiceName("service2");

        objectOutputStream.writeObject(task2);
        objectOutputStream.flush();

        Object data = objectInputStream.readObject();
        System.out.println("data=" + data);

        Object data2 = objectInputStream.readObject();
        System.out.println("data2=" + data2);

        s.close();
    }
}
