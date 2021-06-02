package com.narine.android2less1taskapp;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    private SharedPreferences preferences;

    public Prefs(Context context) {
        preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public void saveBoardState () {
        preferences.edit().putBoolean("isShown", true).apply();   //edit открывает преференс для редактирования,
        //put сохраняем
        // apply это применить и сохранить настройку
    }

    public boolean isShown(){
       return preferences.getBoolean("isShown", false); //get показываем
    }

}
