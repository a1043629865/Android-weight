package com.example.practiceproject

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class UserDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_userdetatil)
        //添加返回键
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //将用户账号传入该Activity
        val userNumber: TextView = findViewById(R.id.user_number)
        val uN = intent.getStringExtra("number").toString()
        userNumber.text = uN


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //点击上方的返回，销毁该Activity
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}