package com.shubham.rd.richdates;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Shubham on 3/20/2018.
 */

public class JsonData {
    ArrayList<String> emailList;
    String url = "http://192.168.43.200/richdatesapp/GetUserData.php";
    private Context context;
    public ArrayList<String> getData()
    {
        emailList=new ArrayList<String>();

        return emailList;
    }
}
