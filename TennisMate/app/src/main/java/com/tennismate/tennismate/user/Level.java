package com.tennismate.tennismate.user;


public  final class Level {

    public int mNumber;
    public String mName;
    public String mDescription;

    public Level(){}

    public Level(int number, String name, String description){
        this.mNumber = number;
        this.mName = name;
        this.mDescription = description;
    }
}
