package com.kt.hiruskotlin

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder

class BackgroundService:Service {
    lateinit var context:Context

    constructor()
    constructor(context: Context){
        this.context = context
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


}