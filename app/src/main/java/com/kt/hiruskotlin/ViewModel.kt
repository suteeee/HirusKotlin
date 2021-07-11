package com.kt.hiruskotlin

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.firebase.database.FirebaseDatabase
import android.content.Intent
import android.app.Activity


object ViewModel {
    var selectedFragment: Fragment? = null
    var backgroundColor =0
    val db = FirebaseDatabase.getInstance().reference

    fun tabSelect(id: Int, fm: FragmentManager) {

        when(id){
            0->{
                selectedFragment = SearchFragment()
            }
            1->{}

        }
        if (selectedFragment != null) fm.beginTransaction().replace(R.id.layout,selectedFragment!!).commit()
    }

    fun tabUnselect(fm: FragmentManager) {
        if (selectedFragment != null) fm.beginTransaction().remove(selectedFragment!!).commit()
    }

    fun setColor(cnt: Int, tv: TextView):Int{

        var faceId = 0

        if(cnt in 1..10) {
            backgroundColor = Color.parseColor("#800099ff")
            faceId = R.drawable.smile
        }
        else if (cnt in 10..50) {
            backgroundColor = (Color.parseColor("#80FFff33"))
            faceId = R.drawable.nonsmile
        }
        else if (cnt in 50..100) {
            backgroundColor = (Color.parseColor("#80FF9933"))
            faceId = R.drawable.unsmile
        }
        else if (cnt >= 250){
            backgroundColor = (Color.parseColor("#80ff0000"))
            faceId = R.drawable.die
        }
        else {
            backgroundColor = (Color.parseColor("#8099FF99"))
            faceId = R.drawable.ssmile
        }
        tv.setBackgroundColor(backgroundColor)
        return faceId
    }

    fun logIn(userId: String, passWord: String, context: Context) {
        val m = Model.ReadDB(LoadingActivity.context)
        m.readUserData(userId,passWord,context)

    }

    fun getPosition() :String{
        return Model.prefs.position!!
    }

    fun startApp(intent: Intent, backGroundIntent: Intent,context: Context){
        val bt = BackgroundThread(LoadingActivity.context)
        bt.start()
        context.startService(backGroundIntent)
    }

}