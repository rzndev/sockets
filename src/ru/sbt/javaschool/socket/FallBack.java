package ru.sbt.javaschool.socket;

import java.io.Serializable;

public class FallBack implements Serializable {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
