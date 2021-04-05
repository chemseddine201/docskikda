package com.lotech.docskikda;

public class ProfileHandler {
    int id;
    String name;
    String phone;
    String local;


    public ProfileHandler(int id, String name, String phone, String local){
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.local = local;

    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getLocal() {
        return local;
    }



}
