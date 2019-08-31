package com.example.attendance_face;

public class Student {
    private String name;
    private String roll;
    private double index;

    private int gender;
   // private int set;
    private int batch;

    public Student(){}
    public Student(String name,String roll,int gender,int batch){
        this.name = name;
        this.roll = roll;
        this.gender = gender;
        this.batch = batch;
        this.index=0;


        //this.set = set;
    }


    public String getName() {
        return name;
    }

    public String getRoll() {
        return roll;
    }
    //public int getSet() {
    //    return set;
   // }

    public int getGender() {
        return gender;
    }
    public double getIndex(){
        return index;
    }
    public int getBatch() {
        return batch;
    }
}
