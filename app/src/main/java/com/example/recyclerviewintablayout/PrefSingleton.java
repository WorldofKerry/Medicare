package com.example.recyclerviewintablayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PrefSingleton {
    private static PrefSingleton mInstance;
    private Context mContext;

    private SharedPreferences mMyPreferences;

    private PrefSingleton() {

    }

    public static PrefSingleton getInstance() {
        if (mInstance == null) mInstance = new PrefSingleton();
        return mInstance;
    }

    public void Initialize(Context context) {
        mContext = context;
        mMyPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public void writePreference(String key, String value) {
        SharedPreferences.Editor editor = mMyPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void writePreference(String key, List valueList) {
        SharedPreferences.Editor editor = mMyPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(valueList);
        editor.putString(key, json);
        editor.apply();
    }

    public List LoadPreferenceList(String key) {
        List list;
        Gson gson = new Gson();
        String json = mMyPreferences.getString(key, null);
        Type type = new TypeToken<ArrayList<Symptom>>() {
        }.getType();
        list = gson.fromJson(json, type);

        if (list == null) {
            list = new ArrayList<>();
        }

        return list;
    }

}
