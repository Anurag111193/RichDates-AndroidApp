package com.shubham.rd.richdates;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by DELL on 10/13/2017.
 */

public class IntroManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    public IntroManager(Context context) {

        this.context = context;
        sharedPreferences=context.getSharedPreferences("first",0);
        editor=sharedPreferences.edit();
    }
    public void setFirst(boolean isFirst)
    {
        editor.putBoolean("check",isFirst);
        editor.commit();
    }
    public boolean  Check()
    {
       return sharedPreferences.getBoolean("check",true);
    }

}
