package com.kt.hiruskotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.kt.hiruskotlin.LoadingActivity.Companion.prefs
import kotlinx.coroutines.*

class BackgroundThread : Thread {
    lateinit var context: Context
    //lateinit var  backgroundService: BackgroundService
    lateinit var  serviceIntent: Intent

    constructor() {
    }

    constructor(context: Context, backgroundService: BackgroundService) {
        this.context = context
        //this.backgroundService = backgroundService
    }

    fun serviceInit() {
        //backgroundService = BackgroundService(context)
        //serviceIntent = Intent(context, backgroundService.javaClass)
    }

    private fun serviceRun() {
       /*
       파이어베이스 이용, db에서 값 찾아오기, 등 동작구현
        */
    }

    private fun optionCheck() {
        if (prefs.threadStates == "사용 안함") {
            Log.d("SubThread", "disable")
            //context.stopService(serviceIntent)
        } else {
            Log.d("SubThread", "able")
            serviceRun()
        }
    }

    override fun run()= runBlocking<Unit> {
        serviceInit()
        val setServiceState = GlobalScope.async {
            while(true){
                Log.d("Thread", "run");
                delay(1000)
                optionCheck()
            }
        }

        setServiceState.start()

    }

}