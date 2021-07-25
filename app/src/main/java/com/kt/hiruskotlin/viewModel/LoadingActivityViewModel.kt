package com.kt.hiruskotlin.viewModel

import android.content.Context
import com.kt.hiruskotlin.LoadingActivity
import com.kt.hiruskotlin.model.ReadDBModel

class LoadingActivityViewModel {
    fun logIn(userId: String, passWord: String, context: Context) {
        val m = ReadDBModel(LoadingActivity.context)
        m.readUserData(userId, passWord, context)

    }
}