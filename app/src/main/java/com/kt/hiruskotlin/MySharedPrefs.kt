package com.kt.hiruskotlin

import android.content.Context
import android.content.SharedPreferences

class MySharedPrefs(context: Context) {
    private val prefsFileName = "prefs"
    private val threadKeyEdt = "states"
    private val logInKeyEdt = "login"
    private val idKeyEdt = "id"

    private val prefs:SharedPreferences = context.getSharedPreferences(prefsFileName, 0)

    var threadStates: String?
        get() = prefs.getString(threadKeyEdt,"")
        set(value) = prefs.edit().putString(threadKeyEdt,value).apply()

    var logInStates: String?
        get() = prefs.getString(logInKeyEdt,"")
        set(value) = prefs.edit().putString(logInKeyEdt,value).apply()

    var userId: String?
        get() = prefs.getString(idKeyEdt,"")
        set(value) = prefs.edit().putString(idKeyEdt,value).apply()

}