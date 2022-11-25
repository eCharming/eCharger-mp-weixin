package com.example.demo.POJO;

public class Message {
    private String uid;
    private String toUid;
    private String time;
    private String message;

    public Message() {
    }

    public Message(String uid, String toUid, String time, String message) {
        this.uid = uid;
        this.toUid = toUid;
        this.time = time;
        this.message = message;
    }

    public String getUid() {
        return uid;
    }

    public String getToUid() {
        return toUid;
    }

    public String getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setToUid(String toUid) {
        this.toUid = toUid;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "uid='" + uid + '\'' +
                ", toUid='" + toUid + '\'' +
                ", time='" + time + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
