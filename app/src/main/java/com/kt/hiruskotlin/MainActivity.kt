package com.kt.hiruskotlin

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var db:DatabaseReference
    lateinit var rd : Model.ReadDB
    lateinit var _do : String
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = FirebaseDatabase.getInstance().reference
        val prefs = Model.MySharedPrefs(applicationContext)
        rd = Model.ReadDB(this)

        _do = "강원도"//Model.addr[1]

        able.setOnClickListener {
            prefs.threadStates = "사용함"
        }

        disable.setOnClickListener{
            prefs.threadStates = "사용 안함"
        }

        b3.setOnClickListener{
            prefs.logInStates = "false"
            val intent = Intent(applicationContext, LoadingActivity::class.java)
            startActivity(intent)
            finish()
        }

        dbup.setOnClickListener{
            Model.MySharedPrefs(applicationContext).position = "rand"
        }


        tabLayout.getTabAt(0)?.setIcon(R.drawable.search_icon)
        tabLayout.getTabAt(1)?.setIcon(R.drawable.issue_icon)
        tabLayout.getTabAt(2)?.setIcon(R.drawable.home_icon)
        tabLayout.getTabAt(3)?.setIcon(R.drawable.world_icon)
        tabLayout.getTabAt(4)?.setIcon(R.drawable.etc_icon)
        tabLayout.selectTab(tabLayout.getTabAt(2))

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    ViewModel.tabSelect(tab.position, supportFragmentManager)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    ViewModel.tabUnselect(supportFragmentManager)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

    }

    override fun onResume() {
        super.onResume()
        rd.readData()
        setScreen()
    }


    @SuppressLint("SetTextI18n")
    private fun setScreen() {
        GlobalScope.launch {

            while(true){
                if(Model.dataReadFinish) {
                    runOnUiThread {
                        BestDisease_tv.text = rd.getBestDieases(_do)[0]
                        patientcnt_tv.text = "현재 감염자 수 : ${rd.getBestDieases(_do)[1]}"
                        faces_iv.setImageResource(ViewModel.setColor(rd.getBestDieases(_do)[1].toInt(), BestDisease_tv))

                        location_tv.setBackgroundColor(ViewModel.backgroundColor)
                        location_tv.text = ViewModel.getPosition()

                        current_tv.setBackgroundColor(ViewModel.backgroundColor)
                        toolbar.setBackgroundColor(ViewModel.backgroundColor)

                        Human2_tv.text =rd.getSecondDieases(_do)[0]
                        ViewModel.setColor(rd.getSecondDieases(_do)[1].toInt(), Human2_tv)

                        Human3_tv.text =rd.getThirdDieases(_do)[0]
                        ViewModel.setColor(rd.getThirdDieases(_do)[1].toInt(), Human3_tv)
                    }
                    break
                }
                delay(100)
            }

        }
    }

}