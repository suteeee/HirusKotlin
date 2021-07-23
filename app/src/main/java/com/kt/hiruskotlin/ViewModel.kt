package com.kt.hiruskotlin

import android.content.Context
import android.graphics.Color
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


object ViewModel {
    var selectedFragment: Fragment? = null
    var backgroundColor =0
    val db = FirebaseDatabase.getInstance().reference

    fun tabSelect(id: Int, fm: FragmentManager, toolbar: androidx.appcompat.widget.Toolbar) {

        when(id){
            0 -> {
                selectedFragment = SearchFragment()
            }
            1 -> {
                selectedFragment = HotissueFragment()
            }
            3->{
                selectedFragment = WorldMapFragment()
            }

        }
        if (selectedFragment != null) fm.beginTransaction().replace(R.id.layout, selectedFragment!!).commit()
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
        m.readUserData(userId, passWord, context)

    }

    fun getPosition(context: Context) :String{
        return Model.MySharedPrefs(context).position!!
    }

    fun getConfirmedData(context: Context, confirmedArr: ArrayList<Int>, nameArr: ArrayList<String>){
        Model.ReadDB(context).getConfirmedData(confirmedArr,nameArr)
    }


    fun locationStart(context: Context) {
        val bt = BackgroundThread(context)
        bt.start()
    }

    fun search(searchView: SearchView, code:Int,fm: FragmentManager,id: Int) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val web = WebFragment(code)
                if (query != null) {
                    web.urlCode = query
                }
                fm.beginTransaction().replace(id, web).commit()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    fun getCurrentTime():String {
        var i = 0
        val cur = System.currentTimeMillis()
        val mCur = Date(cur)
        val simpleDateFormat  = SimpleDateFormat("MM월 dd일 hh시 mm분 기준")
        return simpleDateFormat.format(mCur)
    }

    fun getWebSite(context: Context){
        Model.getWebSite(context)
    }

}