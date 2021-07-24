package com.kt.hiruskotlin.ViewModel

import android.content.Context
import com.kt.hiruskotlin.Model

class WorldMapViewModel {
    fun getConfirmedData(context: Context, confirmedArr: ArrayList<Int>, nameArr: ArrayList<String>){
        Model.ReadDB(context).getConfirmedData(confirmedArr,nameArr)
    }

}