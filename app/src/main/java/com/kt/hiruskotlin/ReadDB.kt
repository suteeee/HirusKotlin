package com.kt.hiruskotlin

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.logging.Handler
import kotlinx.android.synthetic.main.activity_main.*

class ReadDB(context: Context)
 {
     companion object{
         var finish = false
     }
     val db = FirebaseDatabase.getInstance().reference
     val arr = ArrayList<ArrayList<ArrayList<String>>>()
     var context:Context

    init {
        this.context = context
        val pDialog = ProgressDialog(context)
        pDialog.setCancelable(false)
        pDialog.setMessage("데이터를 불러오는 중입니다.")
        pDialog.show()

        repeat(17){
            arr.add( ArrayList<ArrayList<String>>() )
        }

        val read = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var i = 0
                for (snap in snapshot.children) {

                    if(snap.key == "users") break

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
                arr[0].sortWith(compareByDescending { it[1].toInt() })
                arr[0].forEach{
                    Log.d(it.toString(),"map")
                }

                pDialog.dismiss()
                finish = true
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        db.addListenerForSingleValueEvent(read)


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


}