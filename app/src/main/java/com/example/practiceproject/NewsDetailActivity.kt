package com.example.practiceproject

import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class NewsDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_detail)
        //添加返回键
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //获取上一个activity传过来的值
        val url = intent.getStringExtra("url")
        //浏览器
        val webView = findViewById<WebView>(R.id.news_web_view)
        if (url != null) {
            //加载目标url
            webView.loadUrl(url)
        }
    }
    //返回键的点击事件
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
    }

