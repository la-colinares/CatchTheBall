package com.lacolinares.catchtheball.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Colinares on 5/31/2019.
 */

public class SharedPrefUtil {

    private Context mContext;
    private SharedPreferences sharedPref;

    public SharedPrefUtil(Context mContext) {
        this.mContext = mContext;
        sharedPref = mContext.getSharedPreferences("HIGH_SCORE", Context.MODE_PRIVATE);
    }

    public void setHighScore(int score){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("score", score);
        editor.commit();
    }

    public int getHighScore(){
        return sharedPref.getInt("score", 0);
    }
}
