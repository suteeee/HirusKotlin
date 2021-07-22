package com.kt.hiruskotlin

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_world_map.*

class WorldMapFragment : Fragment() {

    lateinit var scrollView:HorizontalScrollView
    lateinit var a :AlertDialog.Builder
    val ad = Array(10){}
    lateinit var scaleGestureDetector: ScaleGestureDetector
    var mScaleFactor = 1.1f
    var lastScaleFactor = 0f

    val MIN_ZOOM = 1.0f
    val MAX_ZOOM = 2.0f

    var zoomCnt = 0

    val contriesCnt =Array(154){}
    val contries = arrayOf(
        CA, US, GL, MX, GT,
        HD, ELS, BLZ, NCG, CR, PNM, CB, VZ, ECD, PR,
        BZ, BV, PRG, CL, AG, UG, GA, SN, FGA, FOL,
        RS, IS, FL, SW, NW, KZH, MONG, CN, NK, KR,
        ID, NP, BT, BGL, MY, TAI, RAOS, BIET, MAL,IDN,
        PAP, AUS, NWZ, SOL, VNT, NVK, PIZ, KRG, TZK, UZB,
        TRK, IRN, AFG, PAQ, IRK, SUA, YEM, OMAN, AEU, SIR,
        TUR, GRG, AZB, ARM, YRD, ISR, LBN, IZT, RIB, SDN,
        CHD, NZR, AZL, MRC, SSHR, MRT, MALI, SNG, GMB, BRC,
        CRTB, GINI, GNBS, SRR, RAIB, GANA, TOGO, VNG, NIZ, CMR,
        CAR, SSD, ETO, SMR, KNYA, UGD, CNGR, CNG, CGN, GBN,
        AGL, ZBA, TZN, RWD, BRD, MLW, MZB, ZBW, BTW, NMB,
        SAR, RST, EST, MDG, UCR, VLR, FLD, GER, FRNC, SPN,
        ITA, SWS, AST, CHK, SLV, HGR, RMN, BGR, GRC, SRV,
        SLVN, CRT, BSN, CSB, MTN, ABN, NMK, RTN, RTB, ESTN,
        LSB, VGE, NDL, DMK, PRT, MDV, ENG, ISL)
    val confirmedArr = ArrayList<Int>()
    val nameArr = ArrayList<String>()
    lateinit var countryTouchListener:CountryTouchListener

    var dialogcnt = 0
    var selected = 0 //0 코로나 1 테스트 2 테스트

    var nstr = ""
    var touch = false
    var layout: LinearLayout? = null
    var btn1: Button? = null
    var btn2: Button? = null
    var btn3: Button? = null

    fun getData(context: Context) {
        ViewModel.getConfirmedData(context)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView =inflater.inflate(R.layout.fragment_world_map,container,false)

    scaleGestureDetector = ScaleGestureDetector(context, ScaleListener())

        deasease1.setOnClickListener(BtnListener())
        deasease2.setOnClickListener(BtnListener())
        deasease3.setOnClickListener(BtnListener())

        WhitemapView.isHorizontalScrollBarEnabled = true
        WhitemapView.setOnTouchListener { view, motionEvent ->
            scaleGestureDetector.onTouchEvent(motionEvent)
        }

        getData(rootView.context)

        for(i in contries.indices){
            val it = contries[i]
            it.isClickable = true
            it.setOnClickListener(countryTouchListener)

            if (confirmedArr[i] in 1..100000) { //확진자 1명 이상 10만명 이하
                it.setColorFilter(Color.parseColor("#4DFF0000"), PorterDuff.Mode.SRC_ATOP) // 빨강색, 투명도 30%
            } else if (confirmedArr[i] in 100001..1000000) { //10만명~100만명
                it.setColorFilter(Color.parseColor("#66FF0000"), PorterDuff.Mode.SRC_ATOP) //빨강색, 투명도 40%
            } else if (confirmedArr[i] in 1000001..3000000) { //300만명
                it.setColorFilter(Color.parseColor("#80FF0000"), PorterDuff.Mode.SRC_ATOP) //빨강색, 투명도 50%
            } else if (confirmedArr[i] in 3000001..5000000) { //500만명
                it.setColorFilter(Color.parseColor("#99FF0000"), PorterDuff.Mode.SRC_ATOP) // 빨강색, 투명도 60%
            } else if (confirmedArr[i] in 5000001..10000000) { //1000만명
                it.setColorFilter(Color.parseColor("#B3FF0000"), PorterDuff.Mode.SRC_ATOP) // 빨강색, 투명도 70%
            } else if (confirmedArr[i] > 10000000) { //1000만명이상
                it.setColorFilter(Color.parseColor("#CCFF0000"), PorterDuff.Mode.SRC_ATOP) //빨강색, 투명도 80%
            } else { //정보없음 혹은 확진자 0
                it.setColorFilter(Color.parseColor("#1AFF0000"), PorterDuff.Mode.SRC_ATOP) //빨강색, 투명도 10%
            }
        }



        return rootView
    }

}