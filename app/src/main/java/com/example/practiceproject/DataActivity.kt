package com.example.practiceproject

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


class DataActivity(private val num: String) : Fragment() {
    private lateinit var lineChart: LineChart
    private lateinit var entries: List<Entry>

    private val dbHelper = MyDatabaseHelper(MyApplication.context, "healthy.db", 2)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_data, container, false)
        lineChart = view.findViewById(R.id.line_chart)
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setLineChart()
    }

    //添加数据
    private fun setData(number: String) {
        val db = dbHelper.writableDatabase
        entries = ArrayList()
        val cursor =
            db.rawQuery(
                "select * from bodydata where number=?",
                arrayOf(number)
            )
        val count = cursor.count
        for (i in 1..count) {
            if (cursor.moveToNext()) {
                val weight = cursor.getInt(cursor.getColumnIndex("weight"))
                (entries as ArrayList<Entry>).add(Entry(i.toFloat(), weight.toFloat()))
            }

        }
        cursor.close()
    }

    private fun setLineChart() {
        setData(num)
        val dataSet = LineDataSet(entries, "体重变化图")
        dataSet.color = Color.parseColor("#000000")
        dataSet.setCircleColor(Color.parseColor("#000000"))
        dataSet.circleRadius = 6F
        dataSet.lineWidth = 1.5F
        dataSet.valueTextColor = Color.parseColor("#000000")
        dataSet.valueTextSize = 17F
        val rightAxis: YAxis = lineChart.axisRight
        rightAxis.isEnabled = false

        //X坐标轴设置
        val xAxis: XAxis = lineChart.xAxis
        xAxis.textColor = Color.RED
        xAxis.textSize = 15F
        xAxis.setDrawAxisLine(true)
        xAxis.setDrawGridLines(true)
        xAxis.setDrawLabels(true)
        xAxis.position = XAxis.XAxisPosition.BOTTOM//设置X轴标签显示位置

        //图例设置
        val legend: Legend = lineChart.legend
        legend.form = Legend.LegendForm.LINE
        legend.textSize = 15F
        legend.formSize = 15F
        legend.textColor = Color.BLUE

        //描述
        val description: Description = lineChart.description
        description.isEnabled = false
        val lineData = LineData(dataSet)
        lineChart.data = lineData
        lineChart.invalidate() // refresh


    }


}


