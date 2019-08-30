package com.example.attendance_face;

public class Student {
    private String name;
    private String roll;

    //private int gender;
    private int set;
    //private int batch;

    public Student(){}
    public Student(String name,String roll,int set){
        // this.batch = batch;
        // this.gender = gender;

        this.name = name;
        this.roll = roll;
        this.set = set;
    }


    public String getName() {
        return name;
    }

    public String getRoll() {
        return roll;
    }
    public int getSet() {
        return set;
    }

   /* public int getGender() {
        return gender;
    }
    public int getBatch() {
        return batch;
    }*/
}
