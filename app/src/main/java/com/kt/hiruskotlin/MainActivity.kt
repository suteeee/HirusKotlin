package com.kt.hiruskotlin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

    }


}