package com.shubham.rd.richdates;

/**
 * Created by Shubham on 4/2/2018.
 */

public class FriendList {
    private String friendName;
    private String friendImage;

    public FriendList(String friendName, String friendImage) {
        this.friendName = friendName;
        this.friendImage = friendImage;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendImage() {
        return friendImage;
    }

    public void setFriendImage(String friendImage) {
        this.friendImage = friendImage;
    }
}
