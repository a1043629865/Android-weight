package com.example.practiceproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.concurrent.thread
import kotlin.properties.Delegates

class JudgeActivity(private val num: String) : Fragment() {

    private lateinit var textBmi: TextView
    private lateinit var textJudge: TextView
    private lateinit var textRange: TextView
    private lateinit var textDealWeight: TextView

    private var getWeight by Delegates.notNull<Double>()
    private var height by Delegates.notNull<Int>()

    private val dbHelper = MyDatabaseHelper(MyApplication.context, "healthy.db", 2)
    private lateinit var weight: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_judge, container, false)
        weight = view.findViewById(R.id.textview_weight)

        textBmi = view.findViewById(R.id.textview_BMI)
        textJudge = view.findViewById(R.id.textview_judge)
        textRange = view.findViewById(R.id.textview_range)
        textDealWeight = view.findViewById(R.id.textview_ideal_weight)

        return view
    }

    private fun request() {
        thread {
            val request = Request.Builder()
                .url("http://apis.juhe.cn/fapig/calculator/weight?sex=1&role=1&height=" + height + "&weight=" + getWeight + "&key=" + MyApplication.KEY2)
                .build()
            val response = OkHttpClient().newCall(request).execute()
            val json = response.body?.string()
            val weightResponse = Gson().fromJson(json, Response::class.java)
            if (weightResponse != null) {
                val getBmi = weightResponse.result.bmi
                val getRange = weightResponse.result.normalWeight
                val getJudge = weightResponse.result.levelMsg
                val getIdealWeight = weightResponse.result.idealWeight
                activity?.runOnUiThread {
                    textBmi.text = getBmi.toString()
                    textJudge.text = getJudge
                    textRange.text = getRange
                    textDealWeight.text = getIdealWeight.toString()
                }

            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val db = dbHelper.writableDatabase
        val cursor = db.rawQuery("select * from bodydata where number=?", arrayOf(num))
        if (cursor.moveToLast()) {
            val w = cursor.getInt(cursor.getColumnIndex("weight"))
            val h = cursor.getInt(cursor.getColumnIndex("height"))
            height = h
            weight.text = w.toString()
            getWeight = w.toDouble()
        }
        cursor.close()
        request()

    }
}