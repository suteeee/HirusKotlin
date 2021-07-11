package com.kt.hiruskotlin

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationListener
import android.location.LocationManager
import android.preference.PreferenceManager
import android.util.Log
import androidx.core.app.ActivityCompat

import com.google.firebase.database.DatabaseReference
import com.kt.hiruskotlin.LoadingActivity.Companion.prefs
import kotlinx.android.synthetic.main.activity_main.*
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
    }


    private fun serviceRun() {
        val serviceIntent = Intent(context, backgroundService::class.java)
        context.startService(serviceIntent)
    }

    private fun optionCheck() {
        val prefs = Model.MySharedPrefs(context)
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
        backgroundService = BackgroundService()

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