package com.lib.bean;

public class Event<T> {

    private int eventCode = -1;

    private T data;


    public Event() {
    }

    public Event(int eventCode) {
        this.eventCode = eventCode;
    }

    public Event(int eventCode, T data) {
        this.eventCode = eventCode;
        this.data = data;
    }

    public int getEventCode() {
        return eventCode;
    }

    public T getData() {
        return data;
    }
}
