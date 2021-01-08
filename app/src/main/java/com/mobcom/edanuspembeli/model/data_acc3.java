package com.mobcom.edanuspembeli.model;

public class data_acc3 {
    String cek;
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
        this.cek = status;
    }


    public data_acc3(){

    }

    public data_acc3(String cek, String status){
        this.cek=cek;
        this.status=status;
    }
}
