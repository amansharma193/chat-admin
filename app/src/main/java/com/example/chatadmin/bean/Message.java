package com.example.chatadmin.bean;

import java.io.Serializable;

public class Message implements Serializable {
    private String message,id,from,to,date,time,fileType;
    private long timestamp;
    private boolean isFile;

    public Message(String message, String id, String from, String to, String date, String time, long timestamp,boolean isFile,String fileType) {
        this.message = message;
        this.id = id;
        this.from = from;
        this.to = to;
        this.date = date;
        this.timestamp=timestamp;
        this.time = time;
        this.isFile=isFile;
        this.fileType=fileType;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isFile() {
        return isFile;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setFile(boolean file) {
        isFile = file;
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
