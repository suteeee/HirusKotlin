package com.kt.hiruskotlin.viewModel

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.kt.hiruskotlin.R
import com.kt.hiruskotlin.model.WebModel
import kotlinx.android.synthetic.main.fragment_hotissue.view.*
import java.text.SimpleDateFormat
import java.util.*

class HotIssueViewModel(root : View) {
    val rootView = root
    val model = WebModel()

    fun getCurrentTime():String {
        val cur = System.currentTimeMillis()
        val mCur = Date(cur)
        val simpleDateFormat  = SimpleDateFormat(rootView.resources.getString(R.string.get_Current_time))
        return simpleDateFormat.format(mCur)
    }

    fun getWebSite(context: Context){
        model.getWebSite(context)
    }

    fun dataSet(te:Array<MutableLiveData<String>>, t:MutableLiveData<String>) {
        val texts = te
        val title = t

        rootView.time.text = getCurrentTime()
        getWebSite(rootView.context)

        while (true){
            if(model.readIssue){
                val keywords = model.issueKeyword
                title.value = (keywords[0])

                for(i in 0 until 10){
                    var str = keywords[i+1]
                    val cnt = (i+1).toString()
                    val space :String=
                        if(i == 0) "               "
                        else "                    "
                    texts[i].value = str.replaceFirst(cnt, space + cnt + "위    ")
                }
                break
            }
        }
    }

}