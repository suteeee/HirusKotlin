package com.kt.hiruskotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_loding.*

class LoadingActivity : AppCompatActivity() {
    lateinit var db:DatabaseReference

    companion object{
        lateinit var prefs:MySharedPrefs
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loding)
        prefs = MySharedPrefs(applicationContext)

        db = FirebaseDatabase.getInstance().reference
    }

    override fun onResume() {
        super.onResume()
        if( prefs.logInStates == "true") startApp()

        if(prefs.userId != null) userId_et.setText(prefs.userId)


        start_btn.setOnClickListener{
            val id = userId_et.text.toString()
            val pw = pwd_et.text.toString()

            if(id == "" || pw == "") Toast.makeText(applicationContext,"ID 혹은 비밀번호를 입력해주세요.",Toast.LENGTH_SHORT).show()
            else logIn(id, pw) //로그인 값 가져오기
        }

    }

    /*
    * 로그인 안했을때는 안넘어가지게 구현해야함
    * 최초 로그인 이후엔 이화면 스킵
    * */

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
        val backgroundService = BackgroundService()
        val bt = BackgroundThread(applicationContext,backgroundService)

        bt.start()

        startActivity(intent)
        Log.d("load", "메인화면")
        finish()

    }
}