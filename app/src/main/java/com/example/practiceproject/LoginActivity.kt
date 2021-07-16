package com.example.practiceproject

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class LoginActivity : AppCompatActivity() {

    private val dbHelper = MyDatabaseHelper(this, "healthy.db", 2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //定义按钮（注册和登录）
        val buttonLogin: Button = findViewById(R.id.button_log)
        val buttonRegister: Button = findViewById(R.id.button_reg)


        buttonLogin.setOnClickListener {
            val editTextNumber: EditText = findViewById(R.id.editText_number)
            val editTextPassword: EditText = findViewById(R.id.editText_password)
            //获取输入框中的内容
            val num = editTextNumber.text.toString()
            val pass = editTextPassword.text.toString()
            //检测是否是已注册
            if (accountTest(num, pass)) {
                Toast.makeText(this, "Successful login", Toast.LENGTH_SHORT).show()
                //设置目标Activity
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("person", num)
                //提取目标值，传入下一个Activity
                startActivity(intent)
            } else {
                Toast.makeText(this, "Failed login", Toast.LENGTH_SHORT).show()
            }

        }
        //注册点击事件
        buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        //创建数据库和表
        val dbHelper = MyDatabaseHelper(this, "healthy.db", 2)
        val createSql: Button = findViewById(R.id.button_createDatabase)
        createSql.setOnClickListener {
            dbHelper.writableDatabase
        }

        //插入数据
        val insertSql: Button = findViewById(R.id.button_insert)
        insertSql.setOnClickListener {
            val db = dbHelper.writableDatabase
            val values0 = ContentValues().apply {
                put("name", "SLM")
                put("sno", "20187648")
                put("iphone", "18856745784")
                put("number", "101")
                put("password", "123456")
            }
            val values1 = ContentValues().apply {
                put("number", "101")
                put("height", "181")
                put("weight", "93")
                put("time", "2021-07-05")
            }
            val values2 = ContentValues().apply {
                put("number", "102")
                put("height", "181")
                put("weight", "87")
                put("time", "2021-07-06")
            }

            val values3 = ContentValues().apply {
                put("number", "103")
                put("height", "181")
                put("weight", "92")
                put("time", "2021-07-07")
            }
            val values4 = ContentValues().apply {
                put("number", "104")
                put("height", "181")
                put("weight", "85")
                put("time", "2021-07-08")
            }
            val values5 = ContentValues().apply {
                put("number", "101")
                put("height", "181")
                put("weight", "86")
                put("time", "2021-07-09")
            }
            val values6 = ContentValues().apply {
                put("number", "101")
                put("height", "181")
                put("weight", "83")
                put("time", "2021-07-10")
            }
            val values7 = ContentValues().apply {
                put("number", "101")
                put("height", "181")
                put("weight", "71")
                put("time", "2021-07-11")
            }
            val values8 = ContentValues().apply {
                put("number", "105")
                put("height", "181")
                put("weight", "75")
                put("time", "2021-07-12")
            }
            val values9 = ContentValues().apply {
                put("number", "101")
                put("height", "181")
                put("weight", "73")
                put("time", "2021-07-13")
            }
            db.insert("user", null, values0)
            db.insert("bodydata", null, values1)
            db.insert("bodydata", null, values2)
            db.insert("bodydata", null, values5)
            db.insert("bodydata", null, values3)
            db.insert("bodydata", null, values4)
            db.insert("bodydata", null, values5)
            db.insert("bodydata", null, values6)
            db.insert("bodydata", null, values7)
            db.insert("bodydata", null, values8)
            db.insert("bodydata", null, values9)
            Toast.makeText(MyApplication.context,"insert successful!", Toast.LENGTH_SHORT).show()
        }
        //查询
        val query: Button = findViewById(R.id.button_query)
        query.setOnClickListener {
            val db = dbHelper.writableDatabase
            val cursor = db.query("user", null, null, null, null, null, null)
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndex("user_id"))
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val number = cursor.getInt(cursor.getColumnIndex("number"))
                    val password = cursor.getInt(cursor.getColumnIndex("password"))
                    Log.d("Login01", "id is $id")
                    Log.d("Login01", "name is $name")
                    Log.d("Login01", "number is $number")
                    Log.d("Login01", "password is $password")

                } while (cursor.moveToNext())
            }
            cursor.close()
        }

        //删除数据
        val delete:Button=findViewById(R.id.button_del)
        val db = dbHelper.writableDatabase
        delete.setOnClickListener{
            db.execSQL("delete from bodydata")
            Toast.makeText(MyApplication.context,"delete successful!", Toast.LENGTH_SHORT).show()
        }
    }




    //检查用户函数
    private fun accountTest(number: String, password: String): Boolean {
        val db = dbHelper.writableDatabase
        //查询用户信息
        val cursor = db.rawQuery(
            "select number,password from user where number=? and password=?",
            arrayOf(number, password)
        )
        if (cursor.moveToFirst()) {
            cursor.close()
            return true
        }
        return false
    }

}

