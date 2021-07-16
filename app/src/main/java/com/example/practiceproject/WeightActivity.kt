package com.example.practiceproject

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread
import kotlin.properties.Delegates

@Suppress("NAME_SHADOWING")
class WeightActivity(private val num: String) : Fragment() {

    private val dbHelper = MyDatabaseHelper(MyApplication.context, "healthy.db", 2)
    private val weightList = ArrayList<WeightData>()
    private lateinit var refresh: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView

    //存放添加的体重
    private lateinit var w1: String

    //获取该用户的身高
    private var intentHeight by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_weight, container, false)
        recyclerView = view.findViewById(R.id.recycleView)
        refresh = view.findViewById(R.id.weight_swiperefresh)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val inflater = MenuInflater(MyApplication.context)
        inflater.inflate(R.menu.toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    //标题栏选中项的事件
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.plus -> {
                val editText1 = EditText(MyApplication.context)
                AlertDialog.Builder(this.context).apply {
                    setTitle("体重")
                    setView(editText1)
                    setCancelable(false)
                    setPositiveButton("保存") { _: DialogInterface, _: Int ->
                        w1 = editText1.text.toString()
                        val db = dbHelper.writableDatabase
                        if(editText1.text!=null) {
                            if (getSqlTime(getTime)) {
                                val values = ContentValues()
                                values.put("weight", w1)
                                db.update("bodydata", values, "time=?", arrayOf(getTime))

                            } else {
                                db.execSQL(
                                    "insert into bodydata (number,height,weight,time)values (?,?,?,?)",
                                    kotlin.arrayOf(num, intentHeight, w1, getTime)
                                )
                            }
                        }
                    }
                    setNegativeButton("取消") { _: DialogInterface, _: Int -> }
                    show()
                }
            }
            R.id.toolbar_setting -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //查询数据库并放入listView的列表中
    private fun sqlListView() {
        val db = dbHelper.writableDatabase
        val cursor = db.rawQuery(
            "select * from bodydata where number=?",
            arrayOf(num)
        )
        if (cursor.moveToFirst()) {
            do {
                val sqlTime = cursor.getString(cursor.getColumnIndex("time"))
                val weight = cursor.getInt(cursor.getColumnIndex("weight"))
                val height: Int = cursor.getInt(cursor.getColumnIndex("height"))
                val example = WeightData(weight.toDouble(), sqlTime.toString())
                //定义实例，将每一行获得的数据放入实例中，将每一次的实例放在列表中
                weightList.add(example)
                intentHeight = height
            } while (cursor.moveToNext())
        }
        cursor.close()
        activity?.runOnUiThread {
            recyclerView.adapter?.notifyDataSetChanged()
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //在fragment中显示标题栏
        setHasOptionsMenu(true)
        //获取系统时间
        sqlListView()
        //weightListView.adapter = ListViewAdapter(weightList, MyApplication.context)

        recyclerView.layoutManager = LinearLayoutManager(MyApplication.context)
        recyclerView.adapter = WeightAdapter(weightList)
        //下拉刷新
        refresh.setColorSchemeColors(Color.parseColor("#009688"))
        //对refresh的监听
        refresh.setOnRefreshListener {
            thread {
                Thread.sleep(500)
                activity?.runOnUiThread {
                    weightList.clear()
                    sqlListView()
                    refresh.isRefreshing = false
                }
            }
        }

    }

    //recycleView适配器
    inner class WeightAdapter(private val list: ArrayList<WeightData>) :
        RecyclerView.Adapter<WeightAdapter.MyviewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): MyviewHolder {
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.weight_item, parent, false)
            return MyviewHolder(view)
        }

        override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
            val w=list[position]
            holder.time.text=w.time
            holder.weight.text= w.weight.toString()
        }

        override fun getItemCount(): Int {
            return list.size
        }

        inner class MyviewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val time: TextView = view.findViewById(R.id.weight_time)
            val weight: TextView = view.findViewById(R.id.weight_detail)
        }
    }


    //获得系统时间
    private val getTime = getTime()

    @SuppressLint("SimpleDateFormat")
    private fun getTime(): String {
        val df = SimpleDateFormat("yyyy-MM-dd") //设置日期格式
        return df.format(Date())
    }

    //获取数据库中的日期
    private fun getSqlTime(time: String): Boolean {
        var time1 = ""
        val db = dbHelper.writableDatabase
        val cursor = db.query("bodydata", null, null, null, null, null, null)
        if (cursor.moveToLast()) {
            time1 = cursor.getString(cursor.getColumnIndex("time"))
        }
        cursor.close()
        return time1 == time
    }
}


