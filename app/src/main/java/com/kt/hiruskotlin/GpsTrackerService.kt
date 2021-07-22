package com.kt.hiruskotlin

import android.Manifest
import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.content.getSystemService

class GpsTrackerService : Service, LocationListener{
    lateinit var context: Context
    lateinit var location:Location
    var latitude = 0.0
    var longitude = 0.0

    private val MIN_DISTANCE_CHANGE_FOR_UPDATES: Float = 10F
    private val MIN_TIME_BW_UPDATES = (1000 * 60 * 1).toLong()
    lateinit var locationManager: LocationManager

    constructor(context: Context){
        this.context = context
        getLocation()
    }

    @SuppressLint("MissingPermission")
    fun getLocation() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this)
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this)
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)!!
            latitude = location.latitude
            longitude = location.longitude
        },0)

    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onLocationChanged(location: Location) {
        latitude = location.latitude
        longitude = location.longitude
    }
}