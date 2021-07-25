package com.kt.hiruskotlin.viewModel

import android.content.Context
import com.kt.hiruskotlin.model.ReadDBModel

class WorldMapViewModel {
    fun getConfirmedData(context: Context, confirmedArr: ArrayList<Int>, nameArr: ArrayList<String>){
        ReadDBModel(context).getConfirmedData(confirmedArr,nameArr)
    }

}