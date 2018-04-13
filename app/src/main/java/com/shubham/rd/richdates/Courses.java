package com.shubham.rd.richdates;

/**
 * Created by DELL on 8/7/2017.
 */

public class Courses {

    private String courseName;
   private String yourEmailList;

    public Courses(String courseName, String yourEmailList) {
        this.courseName = courseName;
        this.yourEmailList = yourEmailList;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public String getYourEmailList() {
        return yourEmailList;
    }

    public void setYourEmailList(String yourEmailList) {
        this.yourEmailList = yourEmailList;
    }

}
