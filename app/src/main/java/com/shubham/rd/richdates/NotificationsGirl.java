package com.shubham.rd.richdates;

import android.util.Log;

/**
 * Created by Shubham on 3/25/2018.
 */

public class NotificationsGirl
{
private String boyName;

    public NotificationsGirl(String boyName) {
        this.boyName = boyName;
    }

    public String getBoyName() {
        return boyName;
    }

    public void setBoyName(String boyName) {
        Log.d("Boy Name",boyName);
        this.boyName = boyName;
    }
}
