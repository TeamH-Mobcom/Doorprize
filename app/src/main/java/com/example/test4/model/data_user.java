package com.example.test4.model;

public class data_user {
    String cek;
    String key;
    String status;
    public String getCek() {
        return cek;
    }

    public void setCek(String cek) {
        this.cek = cek;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public data_user() {

    }


    public data_user(String cek, String status) {
        this.status=status;
        this.cek=cek;
    }
}
