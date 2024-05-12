package com.example.phoneumbra.Domain;

public class Time {
    private int Id;
    private String Name;

    public Time(){

    }

    @Override
    public String toString() {
        return Name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
