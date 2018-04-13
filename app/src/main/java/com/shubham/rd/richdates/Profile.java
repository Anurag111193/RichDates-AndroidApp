package com.shubham.rd.richdates;

/**
 * Created by Shubham on 3/20/2018.
 */


    public class Profile {


        private String name;
        private String imageName;
        private String dob;

    public Profile(String name, String imageName, String dob) {
        this.name = name;
        this.imageName = imageName;
        this.dob = dob;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
