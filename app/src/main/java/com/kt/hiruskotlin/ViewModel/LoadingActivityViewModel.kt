package com.kt.hiruskotlin.ViewModel

import android.content.Context
import com.kt.hiruskotlin.LoadingActivity
import com.kt.hiruskotlin.Model

class LoadingActivityViewModel {
    fun logIn(userId: String, passWord: String, context: Context) {
        val m = Model.ReadDB(LoadingActivity.context)
        m.readUserData(userId, passWord, context)

    }
}