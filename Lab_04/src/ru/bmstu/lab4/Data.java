package ru.bmstu.lab4;

import java.io.Serializable;

public class Data implements Serializable {

    private String name;

    private Byte[] bytes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte[] getBytes() {
        return bytes;
    }

    public void setBytes(Byte[] bytes) {
        this.bytes = bytes;
    }

}
