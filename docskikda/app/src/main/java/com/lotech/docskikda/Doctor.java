package com.lotech.docskikda;


public class Doctor {
    int id;
    private String name;
    private String phone;
    private String local;
    private String speciality_id;

    public Doctor(int id, String name, String phone, String local, String speciality_id){
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.local = local;
        this.speciality_id = speciality_id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }


    public String getLocal() {
        return local;
    }

    public String getSpeciality_id() {
        return speciality_id;
    }

}


