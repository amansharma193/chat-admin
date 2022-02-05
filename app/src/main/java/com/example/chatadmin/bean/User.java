package com.example.chatadmin.bean;

import java.io.Serializable;

public class User implements Serializable {
    private String name,type,id;

    public User(String name, String type,String id) {
        this.name = name;
        this.type = type;
        this.id=id;
    }

    public User() {}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "user='" + name + '\'' +
                ", type='" + type + '\'' +
                "id='"+id+'\''+
                '}';
    }
}
