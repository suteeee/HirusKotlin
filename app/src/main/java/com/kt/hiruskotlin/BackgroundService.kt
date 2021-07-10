package com.kt.hiruskotlin

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Address
import android.location.Geocoder
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.google.firebase.database.*

/*
주기적으로 웹 크롤링
사용자 위치 갱신
푸시 알림
 */
class BackgroundService:Service(){

    lateinit var context:Context
    lateinit var db: DatabaseReference
    var latitude :Double= 0.0
    var longitude :Double= 0.0
    lateinit var geocoder:Geocoder
    var dbReadingDone = false
    lateinit var prefs:MySharedPrefs

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun locationSearch() {
        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val mLocationListener = LocationListener {
            val gpsTrackerService = GpsTrackerService(context)
            latitude = gpsTrackerService.latitude
            longitude = gpsTrackerService.longitude
            geocoder = Geocoder(context)

            var list :List<Address>  = geocoder.getFromLocation(latitude, longitude, 10)

            val addr = list.get(0).toString().split(" ")
            val userId = prefs.userId

            var newStr = addr[1] + " " + addr[2]
            prefs.position = newStr


            val read = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val post = snapshot.child("lastPosition").value.toString() // 로그인할때 등록된 이름이 있는지 확인
                    Log.d("new", newStr)
                    Log.d("post", post)
                    if(post != newStr){
                        Log.d("sdfhgs", "different")
                        //NOTIFICATION
                        noti()
                    }
                    db.child("users").child(userId!!).child("lastPosition").setValue("${addr[1]} ${addr[2]}")
                }
                override fun onCancelled(error: DatabaseError) {}
            }
            db.child("users").child(userId!!).addListenerForSingleValueEvent(read)
        }

        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1f, mLocationListener)
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1f, mLocationListener)
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("Bs", "created")
        context = applicationContext
        db = FirebaseDatabase.getInstance().reference
        prefs = MySharedPrefs(applicationContext)
        locationSearch()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("bs", "startCom")
        super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    fun noti() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notiIntent = Intent(context, LoadingActivity::class.java)
        notiIntent
            .putExtra("NOTI_ID", 0)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            notiIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(context, "1")
            .setContentTitle("해당 지역의 감염병 정보입니다.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setLargeIcon(BitmapFactory.decodeResource(resources,R.drawable.hicon))

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setSmallIcon(R.drawable.ic_launcher_foreground)
            val channerName: CharSequence = "해당 지역의 감염병 정보입니다."
            val description = "Hirus"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel("1", channerName, importance)
            channel.description = (description)

            if (BuildConfig.DEBUG && notificationManager == null) {
                error("Assertion failed")
            }
            notificationManager.createNotificationChannel(channel)
        }else
            builder.setSmallIcon(R.mipmap.ic_launcher)
        notificationManager.notify(1234,builder.build())

    }

}