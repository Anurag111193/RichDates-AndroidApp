package com.shubham.rd.richdates;

/**
 * Created by Shubham on 3/21/2018.
 */

public class Notifications {

    private String myEmail;

    public String getMyEmail() {
        return myEmail;
    }

    public void setMyEmail(String myEmail) {
        this.myEmail = myEmail;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public String getYourEmail() {
        return yourEmail;
    }

    public void setYourEmail(String yourEmail) {
        this.yourEmail = yourEmail;
    }

    public String getYourName() {
        return yourName;
    }

    public void setYourName(String yourName) {
        this.yourName = yourName;
    }

    private String myName;
    private String yourEmail;
    private String yourName;


    public Notifications(String myEmail, String myName, String yourEmail, String yourName) {
        this.myEmail = myEmail;
        this.myName = myName;
        this.yourEmail = yourEmail;
        this.yourName = yourName;
    }
}
