package com.kt.hiruskotlin

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.HorizontalScrollView
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.kt.hiruskotlin.model.ReadDBModel
import com.kt.hiruskotlin.viewModel.WorldMapViewModel
import kotlinx.android.synthetic.main.fragment_world_map.*
import kotlinx.android.synthetic.main.fragment_world_map.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WorldMapFragment : Fragment() {

    companion object{
        var str = ""
        var  dialogcnt = 0
        val ad = arrayOfNulls<AlertDialog>(10)

        var mScaleFactor = 1.1f
        var lastScaleFactor = 0f
        val MIN_ZOOM = 1.0f
        val MAX_ZOOM = 2.0f
        var zoomCnt = 0

        var scrollView : HorizontalScrollView? = null
    }

    val viewModel = WorldMapViewModel()
    lateinit var scaleGestureDetector: ScaleGestureDetector

    val confirmedArr = ArrayList<Int>()
    val nameArr = ArrayList<String>()
    lateinit var countryTouchListener:CountryTouchListener
    lateinit var model:ReadDBModel

    fun getData(context: Context, confirmedArr: ArrayList<Int>, nameArr: ArrayList<String>) {
        viewModel.getConfirmedData(context, confirmedArr, nameArr)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView =inflater.inflate(R.layout.fragment_world_map,container,false)
        model = ReadDBModel(rootView.context)

        scrollView = WhitemapView
        val pDialog = ProgressDialog(context)
        pDialog.setCancelable(false)
        pDialog.setMessage("데이터를 불러오는 중입니다.")
        pDialog.show()

        var contries = arrayOf(
            rootView.CA, rootView.US, rootView.GL, rootView.MX, rootView.GT,
            rootView.HD, rootView.ELS, rootView.BLZ, rootView.NCG, rootView.CR, rootView.PNM, rootView.CB, rootView.VZ, rootView.ECD, rootView.PR,
            rootView.BZ, rootView.BV, rootView.PRG, rootView.CL, rootView.AG, rootView.UG, rootView.GA, rootView.SN, rootView.FGA, rootView.FOL,
            rootView.RS, rootView.IS, rootView.FL, rootView.SW, rootView.NW, rootView.KZH, rootView.MONG, rootView.CN, rootView.NK, rootView.KR,
            rootView.ID, rootView.NP, rootView.BT, rootView.BGL, rootView.MY, rootView.TAI, rootView.RAOS, rootView.BIET, rootView.MAL, rootView.IDN,
            rootView.PAP, rootView.AUS, rootView.NWZ, rootView.SOL, rootView.VNT, rootView.NVK, rootView.PIZ, rootView.KRG, rootView.TZK,rootView. UZB,
            rootView.TRK, rootView.IRN, rootView.AFG, rootView.PAQ, rootView.IRK, rootView.SUA, rootView.YEM, rootView.OMAN, rootView.AEU, rootView.SIR,
            rootView.TUR, rootView.GRG, rootView.AZB, rootView.ARM, rootView.YRD, rootView.ISR, rootView.LBN, rootView.IZT, rootView.RIB,rootView. SDN,
            rootView.CHD, rootView.NZR, rootView.AZL, rootView.MRC, rootView.SSHR, rootView.MRT, rootView.MALI, rootView.SNG, rootView.GMB, rootView.BRC,
            rootView.CRTB, rootView.GINI, rootView.GNBS, rootView.SRR, rootView.RAIB, rootView.GANA, rootView.TOGO, rootView.VNG, rootView.NIZ, rootView.CMR,
            rootView.CAR, rootView.SSD, rootView.ETO, rootView.SMR, rootView.KNYA, rootView.UGD, rootView.CNGR, rootView.CNG, rootView.CGN, rootView.GBN,
            rootView.AGL, rootView.ZBA, rootView.TZN, rootView.RWD, rootView.BRD, rootView.MLW, rootView.MZB, rootView.ZBW, rootView.BTW, rootView.NMB,
            rootView.SAR, rootView.RST, rootView.EST, rootView.MDG, rootView.UCR, rootView.VLR, rootView.FLD, rootView.GER, rootView.FRNC, rootView.SPN,
            rootView.ITA, rootView.SWS, rootView.AST, rootView.CHK, rootView.SLV, rootView.HGR, rootView.RMN, rootView.BGR, rootView.GRC, rootView.SRV,
            rootView.SLVN, rootView.CRT, rootView.BSN, rootView.CSB, rootView.MTN, rootView.ABN, rootView.NMK, rootView.RTN, rootView.RTB, rootView.ESTN,
            rootView.LSB, rootView.VGE, rootView.NDL, rootView.DMK, rootView.PRT, rootView.MDV, rootView.ENG, rootView.ISL)

         scaleGestureDetector = ScaleGestureDetector(context, ScaleListener(rootView.WhitemapView))

        rootView.WhitemapView.isHorizontalScrollBarEnabled = true
        rootView.WhitemapView.setOnTouchListener { view, motionEvent ->
            scaleGestureDetector.onTouchEvent(motionEvent)
            return@setOnTouchListener false
        }
        repeat(154) {
            confirmedArr.add(0)
            nameArr.add("")
        }

        countryTouchListener = CountryTouchListener(nameArr,confirmedArr,contries,requireContext())

        val t = Thread {
            GlobalScope.launch {
                getData(rootView.context,confirmedArr,nameArr)
                while (true) {
                    Log.d("read",ReadDBModel.readComfirmFinish.toString())
                    if (ReadDBModel.readComfirmFinish) {
                        break
                    }
                    delay(500)
                }

                activity?.runOnUiThread{
                   for (i in contries.indices) {
                        val it = contries[i]
                        it.setOnTouchListener(countryTouchListener)
                       var colorCode = ""


                        if (confirmedArr[i] in 1..100000) { //확진자 1명 이상 10만명 이하 //빨강색, 투명도 30%
                            colorCode = "#4DFF0000"

                        } else if (confirmedArr[i] in 100001..1000000) { //10만명~100만명 //빨강색, 투명도 40%
                            colorCode = "#66FF0000"

                        } else if (confirmedArr[i] in 1000001..3000000) { //300만명 //빨강색, 투명도 50%
                            colorCode = "#80FF0000"

                        } else if (confirmedArr[i] in 3000001..5000000) { //500만명 // 빨강색, 투명도 60%
                            colorCode = "#99FF0000"

                        } else if (confirmedArr[i] in 5000001..10000000) { //1000만명 // 빨강색, 투명도 70%
                            colorCode = "#B3FF0000"

                        } else if (confirmedArr[i] > 10000000) { //1000만명이상 //빨강색, 투명도 80%
                            colorCode = "#CCFF0000"

                        } else { //정보없음 혹은 확진자 0 //빨강색, 투명도 10%
                            colorCode = "#1AFF0000"
                        }

                       it.setColorFilter(Color.parseColor(colorCode), PorterDuff.Mode.SRC_ATOP)

                    }
                }
                pDialog.dismiss()
            }
        }
        t.start()
        return rootView
    }

    class CountryTouchListener(nameArr: ArrayList<String>, confirmedArr: ArrayList<Int>, countried:Array<ImageView>,context: Context): View.OnTouchListener {
        val n = nameArr
        val c = confirmedArr
        val con = countried
        val context = context
        val alertTitle = "코로나19 감염병 현황"

        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            Log.d("click",v.toString())
            val index = con.indexOf(v)
            str += ("${n[index]} \n 확진자 수 : ${c[index]} 명\n\n")
            dialogcnt++

            val a = AlertDialog.Builder(context)
                .setTitle(alertTitle)
                .setMessage(str)
                .setNeutralButton("확인") { _: DialogInterface, i: Int ->
                    dialogcnt = 0
                    str = ""
                }
            ad[dialogcnt-1] = a.create()
            if(dialogcnt > 1){
                ad[dialogcnt-2]?.dismiss()
            }
            ad[dialogcnt-1]?.show()
        return false
        }

    }

    class ScaleListener(WhitemapView: HorizontalScrollView) : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        val w = WhitemapView
        override fun onScale(detector: ScaleGestureDetector?): Boolean {
            var scaleFactor = detector?.scaleFactor
            zoomCnt++
            if(zoomCnt > 1){
                ad.forEach { it?.dismiss() }
                zoomCnt = 0
                mScaleFactor *= scaleFactor!!
                mScaleFactor = MIN_ZOOM.coerceAtLeast(mScaleFactor.coerceAtMost(MAX_ZOOM))
                lastScaleFactor = scaleFactor

                applyScaleAndTranslation()
            }
            return true
        }

        fun applyScaleAndTranslation() {
            Log.d("scale", mScaleFactor.toString() + "")
            w.scaleX = mScaleFactor
            w.scaleY = mScaleFactor
        }

    }

}