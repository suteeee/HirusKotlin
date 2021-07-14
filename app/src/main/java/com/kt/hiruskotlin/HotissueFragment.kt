package com.kt.hiruskotlin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_hotissue.*
import kotlinx.android.synthetic.main.fragment_hotissue.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HotissueFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_hotissue, container, false)
        dataSet(rootView)
        return rootView
    }

    fun dataSet(rootView : View) {
        val stringArr = arrayOf(rootView.one, rootView.two, rootView.three, rootView.four, rootView.five, rootView.six, rootView.seven, rootView.eight, rootView.nine, rootView.ten)
        rootView.time.text = ViewModel.getCurrentTime()

        Log.d(stringArr[0].toString(),"dsds")
        ViewModel.getWebSite(rootView.context)


        GlobalScope.launch {
            while (true){
                if(Model.readIssue){
                    val keywords = Model.issueKeyword
                    rootView.issueTitle.text = keywords[0]
                    Log.d("235w2",keywords.toString())
                    for(i in stringArr.indices){
                        val str = keywords[i+1]
                        val cnt = (i+1).toString()
                        val space :String=
                            if(i == 0) "               "
                            else "                    "
                        stringArr[i].text  = str.replaceFirst(cnt, space + cnt + "위    ")
                    }
                    break
                }
                delay(100)
            }
        }
    }

}