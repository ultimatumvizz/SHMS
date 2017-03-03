package com.crespoter.healthmonitor;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Sessions {
    private SharedPreferences prefs;
    public boolean isInitialised()
    {
        if(prefs.contains("userName"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public Sessions(Context cntx)
    {
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }
    public void setUserName(String userName)
    {
        prefs.edit().putString("userName",userName).apply();
    }
    public void setUserId(String id)
    {
        prefs.edit().putString("userId",id).apply();
    }
    public String getUserName()
    {

        return prefs.getString("userName","");
    }
    public String getUserId()
    {
        return prefs.getString("userId","");
    }

}
