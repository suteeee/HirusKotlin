package com.kt.hiruskotlin

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.kt.hiruskotlin.model.MySharedPrefsModel
import com.kt.hiruskotlin.model.ReadDBModel
import com.kt.hiruskotlin.viewModel.LoadingActivityViewModel
import kotlinx.android.synthetic.main.activity_loding.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadingActivity : AppCompatActivity() {
    companion object{
        lateinit var prefs:MySharedPrefsModel
        @SuppressLint("StaticFieldLeak")
        lateinit var context : Context
    }

    val viewModel = LoadingActivityViewModel()

    lateinit var db:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loding)
        context = applicationContext
        prefs = MySharedPrefsModel(context)
        db = FirebaseDatabase.getInstance().reference
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
            pwd_et.text = null
        }

    }

    /*
    * 로그인 안했을때는 안넘어가지게 구현해야함
    * 최초 로그인 이후엔 이화면 스킵
    */

    private fun logIn(userId: String, passWord: String) {
        viewModel.logIn(userId,passWord, this)
        GlobalScope.launch {
            while(true){
                if(ReadDBModel.LogInFinish) {
                    startApp()
                    break
                }
                delay(100)
            }
        }

    }

    fun startApp(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()

    }
}