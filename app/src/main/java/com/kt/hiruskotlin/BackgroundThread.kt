package com.kt.hiruskotlin

import android.content.Context
import android.content.Intent
import android.util.Log

import com.kt.hiruskotlin.model.MySharedPrefsModel
import kotlinx.coroutines.*

class BackgroundThread : Thread {
    companion object{
        var running = false
    }

    lateinit var context: Context
    lateinit var  backgroundService: BackgroundService
    lateinit var  serviceIntent: Intent



    constructor(context: Context) {
        this.context = context
        backgroundService = BackgroundService()
    }


    private fun serviceRun() {
        val serviceIntent = Intent(context, backgroundService::class.java)
        context.startService(serviceIntent)
    }

    private fun optionCheck() {
        val prefs = MySharedPrefsModel(context)
        if (prefs.threadStates == "사용 안함") {
            Log.d("SubThread", "disable")
            if(running) context.stopService(serviceIntent) //서비스 실행중일때만 종료가능
        } else {
            Log.d("SubThread", "able")
           if(!running) { //서비스 종료상태일때만 실행가능
               Log.d("SubThread", "start")
               serviceRun()
               running = true
           }
        }
    }

    override fun run()= runBlocking<Unit> {
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