package com.guigu.springcloud.dataobjects;

import java.io.Serializable;

public class Payment implements Serializable{
    //数据库cloud2022数据库中的payment表中的id字段为bigint类型，所以这里使用long
    private long id;
    private String serial;

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", serial='" + serial + '\'' +
                '}';
    }
}
