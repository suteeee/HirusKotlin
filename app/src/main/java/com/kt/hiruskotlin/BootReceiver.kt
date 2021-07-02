package com.kt.hiruskotlin

import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.util.Log

class BootReceiver : BroadcastReceiver() {
    companion object{
        val isServiceRunning = fun (context: Context, serviceClass: Class<*>): Boolean {
            val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            for (service in manager.getRunningServices(Int.MAX_VALUE)) {
                if (serviceClass.name == service.service.className) {
                    Log.i("bootReceiver", "ServiceRunning? = " + true)
                    return true
                }
            }
            Log.i("bootReceiver", "ServiceRunning? = " + false)
            return false
        }
    }


    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent!!.action

        //액션 값이 부팅 완료일때 동작하는 조건문
        if (action == "android.intent.action.BOOT_COMPLETED") {
            //부팅 이후 처리할 동작들 작성
            Log.d("bootReceiver", "ACTION : $action")
            Handler().postDelayed({
                val serviceLuncher = Intent(context, BackgroundService::class.java)
                //기기 버전이 오레오 이상일 경우
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context!!.startForegroundService(serviceLuncher)
                } else {
                    context!!.startService(serviceLuncher)
                }
            }, 3000)
        }
    }
}