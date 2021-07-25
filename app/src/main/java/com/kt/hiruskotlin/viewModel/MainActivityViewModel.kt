package com.kt.hiruskotlin.viewModel

import android.content.Context
import android.graphics.Color
import android.provider.Settings
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.kt.hiruskotlin.*
import com.kt.hiruskotlin.model.MySharedPrefsModel
import com.kt.hiruskotlin.model.ReadDBModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivityViewModel :ViewModel(){
    var selectedFragment: Fragment? = null
    var backgroundColor =0
    var position = MutableLiveData<String>()
    val db = FirebaseDatabase.getInstance().reference
    var bestDieases =MutableLiveData<String>()
    var secondDieases =MutableLiveData<String>()
    var thirdDieases =MutableLiveData<String>()
    var patientCnt = MutableLiveData<String>()
    var faceId = (R.drawable.smile)

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
            4->{
                selectedFragment = UserFragment()
            }

        }
        if (selectedFragment != null) fm.beginTransaction().replace(R.id.layout, selectedFragment!!).commit()
    }

    fun tabUnselect(fm: FragmentManager) {
        if (selectedFragment != null) fm.beginTransaction().remove(selectedFragment!!).commit()
    }

    fun setColor(cnt: Int, tv: TextView){

        if(cnt in 1..10) {
            backgroundColor = Color.parseColor("#800099ff")
        }
        else if (cnt in 10..50) {
            backgroundColor = (Color.parseColor("#80FFff33"))
        }
        else if (cnt in 50..100) {
            backgroundColor = (Color.parseColor("#80FF9933"))
        }
        else if (cnt >= 250){
            backgroundColor = (Color.parseColor("#80ff0000"))
        }
        else {
            backgroundColor = (Color.parseColor("#8099FF99"))
        }
        tv.setBackgroundColor(backgroundColor)
    }

    fun setImage(cnt:Int) {
        if(cnt in 1..10) {
            faceId = R.drawable.smile
        }
        else if (cnt in 10..50) {
            faceId = R.drawable.nonsmile
        }
        else if (cnt in 50..100) {
            faceId = R.drawable.unsmile
        }
        else if (cnt >= 250){
            faceId = R.drawable.die
        }
        else {
            faceId = R.drawable.ssmile
        }
    }


    /*fun setData(context: Context,rd:ReadDBModel) {
        finish = false
            while(ReadDBModel.dataReadFinish){
                position.value = MySharedPrefsModel(context).position.toString()
                //val _do = position.split(" ")[0]
                val best = rd.getBestDieases(_do)
                val sec = rd.getSecondDieases(_do)
                val thi = rd.getThirdDieases((_do))

                bestDieases.value = best[0]


                patientCnt.value = "현재 감염자 수 : ${best[1]}"

                secondDieases.value = sec[0]
                thirdDieases.value = thi[0]

            }
        finish = true
    }*/

    fun locationStart(context: Context) {
        val bt = BackgroundThread(context)
        bt.start()
    }


}