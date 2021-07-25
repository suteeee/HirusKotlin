package com.kt.hiruskotlin.model

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import com.kt.hiruskotlin.GpsTrackerService

class LocationModel {

    var addr1 = ""
    var addr2 = ""
    var addr3 = ""

    var backNoti = false

        var latitude :Double= 0.0
        var longitude :Double= 0.0
        lateinit var geocoder: Geocoder

        fun locationSearch(context: Context) {
            val prefs = MySharedPrefsModel(context)
            val gpsTrackerService = GpsTrackerService(context)
            latitude = gpsTrackerService.latitude
            longitude = gpsTrackerService.longitude
            geocoder = Geocoder(context)

            try {
                while(true){
                    if(latitude != 0.0 && longitude != 0.0) break
                    latitude = gpsTrackerService.latitude
                    longitude = gpsTrackerService.longitude

                }


                var list: List<Address> = geocoder.getFromLocation(latitude, longitude, 10)
                val addr = list.get(0).toString().split(" ")

                addr1 = addr[1]
                addr2 = addr[2]
                addr3 = addr[3]

                var newStr = addr[1] + " " + addr[2]

                if (prefs.position != newStr) {
                    backNoti = true
                }
                Log.d(newStr,"Model")
                prefs.position = newStr
            }
            catch(e:ArrayIndexOutOfBoundsException){
                prefs.position = "알수 없음"
            }
        }

}