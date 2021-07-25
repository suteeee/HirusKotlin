package com.kt.hiruskotlin.model

import android.app.ProgressDialog
import android.content.Context
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

class WebModel {
    var readIssue = false
    var issueKeyword  = List<String>(10){""}
    fun getWebSite(context: Context){

        val pDialog = ProgressDialog(context)
        pDialog.setCancelable(false)
        pDialog.setMessage("데이터를 불러오는 중입니다.")
        pDialog.show()

        GlobalScope.launch {
            val url = "https://www.kdca.go.kr/search/search.es?mid=a20101000000"
            val doc = Jsoup.connect(url).get()
            val title = doc.select("article.box_keyword")

            issueKeyword = title.text().split(" ")
            readIssue = true
            pDialog.dismiss()
        }
    }
}