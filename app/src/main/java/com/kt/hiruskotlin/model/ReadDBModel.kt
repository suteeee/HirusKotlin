package com.kt.hiruskotlin.model

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kt.hiruskotlin.viewModel.MainActivityViewModel

class ReadDBModel(context: Context) {
    companion object {
        var dataReadFinish = false
        var LogInFinish = false
        var readComfirmFinish = false
    }

    val db = FirebaseDatabase.getInstance().reference
    val arr = ArrayList<ArrayList<ArrayList<String>>>()
    var context: Context = context


    fun readData() {

        dataReadFinish = false
        val pDialog = ProgressDialog(context)
        pDialog.setCancelable(false)
        pDialog.setMessage("데이터를 불러오는 중입니다.")
        pDialog.show()

        repeat(18){
            arr.add( ArrayList<ArrayList<String>>() )
        }

        db.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("do","do")
                var i = 0
                for (snap in snapshot.children) {
                    Log.d(snap.key.toString(),"read")

                    if(snap.key == "users" || snap.key == "corona") break

                    for(subSnap in snap.children){
                        if(subSnap.key != "지역"){
                            var temp = ArrayList<String>()
                            temp.add(subSnap.key!!)
                            temp.add(subSnap.value.toString())
                            arr[i].add(temp)
                        }
                    }
                    i++
                }
                arr.forEach{it.sortWith(compareByDescending { it[1].toInt() })}
                arr[8].forEach{
                    Log.d(it.toString(),"map")
                }

                pDialog.dismiss()
                dataReadFinish = true
                Log.d("do","do")
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d(error.toString(),"dooo")
            }

        })
    }

    fun getBestDieases(pos:String) : ArrayList<String>{
        var temp = ArrayList<String>()
        when(pos){
            "서울" -> temp =  arr[0][0]
            "부산" -> temp =  arr[1][0]
            "대구" -> temp =  arr[2][0]
            "인천" -> temp =  arr[3][0]
            "광주" -> temp =  arr[4][0]
            "대전" -> temp =  arr[5][0]
            "울산" -> temp =  arr[6][0]
            "경기도" -> temp =  arr[7][0]
            "강원도" -> temp =  arr[8][0]
            "충청북도" -> temp =  arr[9][0]
            "충청남도" -> temp =  arr[10][0]
            "전라북도" -> temp =  arr[11][0]
            "전라남도" -> temp =  arr[12][0]
            "경상북도" -> temp =  arr[13][0]
            "경상남도" -> temp =  arr[14][0]
            "제주" -> temp =  arr[15][0]
            "세종" -> temp =  arr[16][0]
        }
        return temp
    }

    fun getSecondDieases(pos:String) : ArrayList<String>{
        var temp = ArrayList<String>()
        when(pos){
            "서울" -> temp =  arr[0][1]
            "부산" -> temp =  arr[1][1]
            "대구" -> temp =  arr[2][1]
            "인천" -> temp =  arr[3][1]
            "광주" -> temp =  arr[4][1]
            "대전" -> temp =  arr[5][1]
            "울산" -> temp =  arr[6][1]
            "경기도" -> temp =  arr[7][1]
            "강원도" -> temp =  arr[8][1]
            "충청북도" -> temp =  arr[9][1]
            "충청남도" -> temp =  arr[10][1]
            "전라북도" -> temp =  arr[11][1]
            "전라남도" -> temp =  arr[12][1]
            "경상북도" -> temp =  arr[13][1]
            "경상남도" -> temp =  arr[14][1]
            "제주" -> temp =  arr[15][1]
            "세종" -> temp =  arr[16][1]
        }
        return temp
    }

    fun getThirdDieases(pos:String) : ArrayList<String>{
        var temp = ArrayList<String>()
        when(pos){
            "서울" -> temp =  arr[0][2]
            "부산" -> temp =  arr[1][2]
            "대구" -> temp =  arr[2][2]
            "인천" -> temp =  arr[3][2]
            "광주" -> temp =  arr[4][2]
            "대전" -> temp =  arr[5][2]
            "울산" -> temp =  arr[6][2]
            "경기도" -> temp =  arr[7][2]
            "강원도" -> temp =  arr[8][2]
            "충청북도" -> temp =  arr[9][2]
            "충청남도" -> temp =  arr[10][2]
            "전라북도" -> temp =  arr[11][2]
            "전라남도" -> temp =  arr[12][2]
            "경상북도" -> temp =  arr[13][2]
            "경상남도" -> temp =  arr[14][2]
            "제주" -> temp =  arr[15][2]
            "세종" -> temp =  arr[16][2]
        }
        return temp
    }

    fun getConfirmedData(confirmedArr: ArrayList<Int>, nameArr: ArrayList<String>) : ArrayList<Int>{
        readComfirmFinish = false
        val countriCode =
            arrayOf("CA","US","GL","MX","GT","HD","ELS","BLZ","CR","PNM",
                "PNM","CB","VZ","ECD","PR","BZ","BV","PRG","CL","AG",
                "UG","GA","SN","FGA","FOL","RS","IS","FL","SW", "NW",
                "KZH","MONG","CN","NK","KR","ID","NP","BT","BGL", "MY",
                "TAI","RAOS","BIET","MAL","IDN","PAP","AUS","NWZ","SOL","VNT",
                "NVK","PIZ","KRG","TZK","UZB","TRK","IRN","AFG","PAQ", "IRK",
                "SUA","YEM","OMAN","AEU","SIR","TUR","GRG","AZB", "ARM","YRD",
                "ISR","LBN","IZT","RIB","SDN","CHD","NZR","AZL", "MRC", "SSHR",
                "MRT","MALI","SNG","GMB","BRC","CRTB","GINI","GNBS", "SRR", "RAIB",
                "GANA","TOGO","VNG","NIZ","CMR","CAR","SSD","ETO", "SMR", "KNYA",
                "UGD","CNGR","CNG","CGN","GBN","AGL","ZBA","TZN", "RWD", "BRD",
                "MLW","MZB","NCG","ZBW","BTW","NMB","SAR","RST","EST", "MDG", "UCR",
                "VLR","FLD","GER","FRNC","SPN","ITA","SWS","AST", "CHK", "SLV",
                "HGR","RMN","BGR","GRC","SRV","SLVN","CRT","BSN", "CSB", "MTN",
                "ABN","NMK","RTN","RTB","ESTN","LSB","VGE","NDL", "DMK", "PRT",
                "MDV","ENG","ISL")

        val read = object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("read","read")
                snapshot.children.forEach {
                    Log.d("read","read")
                    val post = it
                    val d = post.key.toString()
                    val index = countriCode.indexOf(d)

                    confirmedArr[index] =  post.children.elementAt(0).value.toString().toInt()
                    nameArr[index] =  post.children.elementAt(0).key.toString()
                }
                readComfirmFinish = true
                Log.d("readFinish",readComfirmFinish.toString())
            }

        }
        db.child("corona").addListenerForSingleValueEvent(read)
        return confirmedArr
    }

    fun readUserData(userId:String, passWord:String,context: Context) {
        val prefs = com.kt.hiruskotlin.model.MySharedPrefsModel(context)
        class User {
            val userId = userId
            val passWord = passWord
        }
        val user = User()

        val pDialog = ProgressDialog(context)
        pDialog.setCancelable(false)
        pDialog.setMessage("로그인 중입니다.")
        pDialog.show()

        val read = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val post = snapshot.child(userId) // 로그인할때 등록된 이름이 있는지 확인
                if (post.value == null) { //등록 내역이 없다면
                    MainActivityViewModel().db.child("users").child(userId).setValue(user)
                    LogInFinish = true
                } else {
                    val check = post.child("passWord").value.toString()
                    Log.d(check, "sdf")
                    if (!check.equals(passWord)) {
                        Toast.makeText(context, "비밀번호를 잘못 입력하셧습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        prefs.logInStates = "true" //기기에 유저정보 저장
                        LogInFinish = true
                    }

                }
                pDialog.dismiss()
            }
            override fun onCancelled(error: DatabaseError) {}
        }
        prefs.userId = userId
        MainActivityViewModel().db.child("users").addValueEventListener(read)

    }
}