package com.kt.hiruskotlin

import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.firebase.database.*
import com.kt.hiruskotlin.databinding.ActivityMainBinding
import com.kt.hiruskotlin.model.MySharedPrefsModel
import com.kt.hiruskotlin.model.ReadDBModel
import com.kt.hiruskotlin.viewModel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var db:DatabaseReference
    lateinit var rd : ReadDBModel
    lateinit var _do : String
    lateinit var binding:ActivityMainBinding

    lateinit var viewModel:MainActivityViewModel

    @RequiresApi(Build.VERSION_CODES.Q)
    var REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_BACKGROUND_LOCATION
    )

    private fun checkPermissions():Boolean {
        return (ActivityCompat.checkSelfPermission(this,REQUIRED_PERMISSIONS[0]) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,REQUIRED_PERMISSIONS[1]) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,REQUIRED_PERMISSIONS[2]) == PackageManager.PERMISSION_GRANTED)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var check = true
        if(requestCode == 100){
            grantResults.forEach {
                if(it == PackageManager.PERMISSION_DENIED) check = false
            }

            if(check){ }
            else{
                val snackbar = Snackbar.make(container, "위치권한 설정 요망", Snackbar.LENGTH_INDEFINITE)
                    .setAction("설정하기"){
                        val permissionIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,Uri.parse("package:" + packageName))
                        permissionIntent.addCategory(Intent.CATEGORY_DEFAULT)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        try {
                            startActivity(permissionIntent)
                        }catch (e:ActivityNotFoundException){
                            startActivity(permissionIntent)
                        }
                    }
                    snackbar.show()
            }
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.apply {
            lifecycleOwner = this@MainActivity
            view = viewModel
            invalidateAll()
        }


        if(!checkPermissions()){
            ActivityCompat.requestPermissions(this,REQUIRED_PERMISSIONS,100)
        }


        db = FirebaseDatabase.getInstance().reference
        val prefs = MySharedPrefsModel(applicationContext)
        rd = ReadDBModel(this)

        able.setOnClickListener {
            prefs.threadStates = "사용함"
        }

        disable.setOnClickListener{
            prefs.threadStates = "사용 안함"
            viewModel.bestDieases.value = "엄준식"
        }

        b3.setOnClickListener{
            prefs.logInStates = "false"
            val intent = Intent(applicationContext, LoadingActivity::class.java)
            startActivity(intent)
            finish()
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
                    viewModel.tabSelect(tab.position, supportFragmentManager,toolbar)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewModel.tabUnselect(supportFragmentManager)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })



    }

    override fun onResume() {
        super.onResume()
        if(checkPermissions()){
            viewModel.locationStart(applicationContext)
            rd.readData()

            setScreen()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setScreen() {
        val prefs = MySharedPrefsModel(applicationContext)
            GlobalScope.launch {

                while(true){
                    if(ReadDBModel.dataReadFinish){
                        _do = prefs.position.toString().split(" ")[0]

                        while (true){
                            if(_do != null && _do != "rand") break
                            _do = prefs.position.toString().split(" ")[0]
                        }

                        viewModel.bestDieases.postValue(rd.getBestDieases(_do)[0])
                        viewModel.patientCnt.postValue(rd.getBestDieases(_do)[1])
                        viewModel.position.postValue(prefs.position)
                        viewModel.setImage(rd.getBestDieases(_do)[1].toInt())

                        viewModel.secondDieases.postValue((rd.getSecondDieases(_do)[0]))
                        viewModel.thirdDieases.postValue((rd.getThirdDieases(_do)[0]))

                        runOnUiThread {
                            viewModel.setColor(rd.getBestDieases(_do)[1].toInt(),location_tv)
                            BestDisease_tv.setBackgroundColor(viewModel.backgroundColor)
                            current_tv.setBackgroundColor(viewModel.backgroundColor)
                            toolbar.setBackgroundColor(viewModel.backgroundColor)
                            faces_iv.setImageResource(viewModel.faceId)

                            viewModel.setColor(rd.getSecondDieases(_do)[1].toInt(), Human2_tv)
                            viewModel.setColor(rd.getThirdDieases(_do)[1].toInt(), Human3_tv)
                        }
                        break
                    }

                }
                delay(100)
            }
        binding.invalidateAll()
    }

}