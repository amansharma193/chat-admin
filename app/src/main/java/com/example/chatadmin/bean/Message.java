package com.example.chatadmin.bean;

import java.io.Serializable;

public class Message implements Serializable {
    private String message,id,from,to,date,time;
    private long timestamp;

    public Message(String message, String id, String from, String to, String date, String time, long timestamp) {
        this.message = message;
        this.id = id;
        this.from = from;
        this.to = to;
        this.date = date;
        this.timestamp=timestamp;
        this.time = time;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Message(String message, String from, String to, String date, String time) {
        this.message = message;
        this.from = from;
        this.to = to;
        this.date = date;
        this.time = time;
    }
    public Message() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
