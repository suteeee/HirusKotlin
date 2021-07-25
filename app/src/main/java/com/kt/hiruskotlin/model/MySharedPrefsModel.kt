package com.kt.hiruskotlin.model

import android.content.Context
import android.content.SharedPreferences

class MySharedPrefsModel (context: Context) {
        private val prefsFileName = "prefs"
        private val threadKeyEdt = "states"
        private val logInKeyEdt = "login"
        private val posKeyEdt = "pos"
        private val idKeyEdt = "id"

        private val prefs: SharedPreferences = context.getSharedPreferences(prefsFileName, 0)

        var threadStates: String?
            get() = prefs.getString(threadKeyEdt,"")
            set(value) = prefs.edit().putString(threadKeyEdt,value).apply()

        var logInStates: String?
            get() = prefs.getString(logInKeyEdt,"")
            set(value) = prefs.edit().putString(logInKeyEdt,value).apply()

        var userId: String?
            get() = prefs.getString(idKeyEdt,"")
            set(value) = prefs.edit().putString(idKeyEdt,value).apply()

        var position: String?
            get() = prefs.getString(posKeyEdt,"")
            set(value) = prefs.edit().putString(posKeyEdt,value).apply()

}