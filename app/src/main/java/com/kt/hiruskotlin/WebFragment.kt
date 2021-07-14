package com.kt.hiruskotlin

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment

class WebFragment(val webFlag: Int) : Fragment() {
    lateinit var webview: WebView
    lateinit var webSettings: WebSettings
    var urlCode = "a20101000000"


    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_web, container, false)
        var url: String = ""
        if (webFlag == 0) {
            url = "https://www.kdca.go.kr/search/" +
                    "search.es?mid=a20101000000&termType=A&kwd=$urlCode&category=TOTAL&reSrchFlag=false&pageNum=1&pageSize=10&detailSearch=false&srchFd=TOTAL&" +
                    "sort=d&date=TOTAL&startDate=&endDate=&fileExt=TOTAL&writer=&year=TOTAL&site=CDC&preKwd=%EC%BD%94%EB%A1%9C%EB%82%98"
        } else if (webFlag == 1) {
            url = "https://www.kdca.go.kr/npt/biz/npp/portal/nppSumryMain.do?icdCd=$urlCode&icdSubgrpCd="
        }
        webview = rootView.findViewById(R.id.webView)
        webSettings = webview.settings
        webSettings.javaScriptEnabled = true
        webSettings.setSupportMultipleWindows(false)
        webSettings.javaScriptCanOpenWindowsAutomatically = false
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
        webSettings.setSupportZoom(true)
        webSettings.builtInZoomControls = true
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        webSettings.domStorageEnabled = true
        webview.loadUrl(url)
        return rootView
    }
}