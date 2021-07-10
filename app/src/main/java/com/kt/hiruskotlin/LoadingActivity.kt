package com.kt.hiruskotlin

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_loding.*

class LoadingActivity : AppCompatActivity() {
    companion object{
        lateinit var prefs:MySharedPrefs
    }

    lateinit var db:DatabaseReference

    private val PERMISSIONS_REQUEST_CODE = 100
    private val GPS_ENABLE_REQUEST_CODE = 2001
    var REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_BACKGROUND_LOCATION
    )

    fun checkPermissions() : Boolean{
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        val hasBackgroundPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
            hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED &&
            hasBackgroundPermission == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        } else
            return false
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == PERMISSIONS_REQUEST_CODE && grantResults.size == REQUIRED_PERMISSIONS.size){
            var check_result = true

            for(result in grantResults){
                if(result != PackageManager.PERMISSION_GRANTED){
                    check_result = false
                    break
                }
            }

            if(check_result){

            }else{

                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this, REQUIRED_PERMISSIONS[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                        this, REQUIRED_PERMISSIONS[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                        this, REQUIRED_PERMISSIONS[2]
                    )) {
                    Snackbar.make(
                        findViewById(R.id.Loding_lt), "이 앱을 실행하려면 위치 접근 권한이 필요합니다.",
                        Snackbar.LENGTH_INDEFINITE).setAction("확인") {
                        ActivityCompat.requestPermissions(this@LoadingActivity, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE)
                        finish()}.show()
                } else {
                    Snackbar.make(
                        findViewById(R.id.Loding_lt), "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ",
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction("확인") { finish() }.show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loding)
        prefs = MySharedPrefs(applicationContext)

        db = FirebaseDatabase.getInstance().reference

        if(!checkPermissions()){
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE)
        }
    }

    override fun onResume() {
        super.onResume()
        if( prefs.logInStates == "true") startApp()

        if(prefs.userId != null) userId_et.setText(prefs.userId)


        start_btn.setOnClickListener{
            val id = userId_et.text.toString()
            val pw = pwd_et.text.toString()

            if(id == "" || pw == "") Toast.makeText(
                applicationContext,
                "ID 혹은 비밀번호를 입력해주세요.",
                Toast.LENGTH_SHORT
            ).show()
            else logIn(id, pw) //로그인 값 가져오기
        }

    }

    /*
    * 로그인 안했을때는 안넘어가지게 구현해야함
    * 최초 로그인 이후엔 이화면 스킵
    */

    private fun logIn(userId: String, passWord: String) {
        class User {
            val userId = userId
            val passWord = passWord
            val lastPosition = ""
        }
        val user = User()

        val read = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val post = snapshot.child(userId) // 로그인할때 등록된 이름이 있는지 확인
                if (post.value == null) { //등록 내역이 없다면
                    db.child("users").child(userId).setValue(user)
                    startApp()
                } else {
                    val check = post.child("passWord").value.toString()
                    Log.d(check, "sdf")
                    if (!check.equals(passWord)) {

                        Toast.makeText(applicationContext, "비밀번호를 잘못 입력하셧습니다.", Toast.LENGTH_SHORT).show()
                        pwd_et.text = null
                    } else {
                        prefs.logInStates = "true" //기기에 유저정보 저장
                        startApp()
                    }

                }
            }
            override fun onCancelled(error: DatabaseError) {}
        }
        prefs.userId = userId
        db.child("users").addValueEventListener(read)
    }

    private fun startApp(){
        val intent = Intent(this, MainActivity::class.java)
        val bt = BackgroundThread(applicationContext)

        bt.start()
        val b = BackgroundService()
        val aintent = Intent(applicationContext, b::class.java)
        applicationContext.startService(aintent)

        startActivity(intent)
        Log.d("load", "메인화면")
        finish()

    }
}