package ru.kos.ix.dto;

import java.io.Serializable;

/**
 * Created by Константин on 08.04.2016.
 */
public class AnsTask implements Serializable {

    private final static long serialVersionUID = -7401961224124280149l;

    private Integer id;
    private Object result;

    public AnsTask(Integer id, Object result) {
        this.id = id;
        this.result = result;
    }

    public Integer getId() {
        return id;
    }

    public Object getResult() {
        return result;
    }
}
