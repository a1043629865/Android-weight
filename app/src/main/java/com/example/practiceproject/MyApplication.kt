package com.example.practiceproject

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class MyApplication : Application() {
    //静态变量
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        const val KEY1 = "3c6335d9ac13c0a0e28ac7e45e79c4dd"

        const val KEY2 = "32d3f325c022a932a78ba5a730838a5f"
    }

    override fun onCreate() {
        super.onCreate()
        context = baseContext
    }
}