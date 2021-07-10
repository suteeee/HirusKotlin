package com.kt.hiruskotlin

import android.app.ProgressDialog
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_loding.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var db:DatabaseReference
    lateinit var rd : ReadDB
    lateinit var _do : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = FirebaseDatabase.getInstance().reference
        val prefs = MySharedPrefs(applicationContext)

        val gpsTrackerService = GpsTrackerService(applicationContext)
        val latitude = gpsTrackerService.latitude
        val longitude = gpsTrackerService.longitude
        val geocoder = Geocoder(applicationContext)

        var list :List<Address>  = geocoder.getFromLocation(latitude, longitude, 10)

        val addr = list.get(0).toString().split(" ")

        _do = addr[1]

        val read = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val post = snapshot.child("lastPosition").value.toString()
            location_tv.text = post
        }

        override fun onCancelled(error: DatabaseError) {}
    }
        rd = ReadDB(this)


        db.child("users").child(LoadingActivity.prefs.userId!!).addListenerForSingleValueEvent(read)


        able.setOnClickListener {
            LoadingActivity.prefs.threadStates = "사용함"
        }

        disable.setOnClickListener{
            LoadingActivity.prefs.threadStates = "사용 안함"
        }

        b3.setOnClickListener{
            LoadingActivity.prefs.logInStates = "false"
            val intent = Intent(applicationContext,LoadingActivity::class.java)
            startActivity(intent)
            finish()
        }

        dbup.setOnClickListener{
            BestDisease_tv.setText(rd.getBestDieases(_do)[0])
        }
        setScreen()

    }

    private fun setScreen() {
        GlobalScope.launch {
            var done = false
            while(!done){
                if(ReadDB.finish) {
                    val read = object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val post = snapshot.child("lastPosition").value.toString()
                            location_tv.text = post
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    }
                    db.child("users").child(LoadingActivity.prefs.userId!!).addListenerForSingleValueEvent(read)

                    runOnUiThread {
                        BestDisease_tv.text = (rd.getBestDieases(_do)[0])
                        patientcnt_tv.text = rd.getBestDieases(_do)[1]
                        Human2_tv.text =rd.getSecondDieases(_do)[0]
                        Human3_tv.text =rd.getThirdDieases(_do)[0]
                    }
                    done = true


                }
                delay(100)
            }

        }
    }

}