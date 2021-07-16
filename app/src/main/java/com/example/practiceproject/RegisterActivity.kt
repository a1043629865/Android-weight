package com.example.practiceproject

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val name: EditText = findViewById(R.id.edittext_name)
        val sno: EditText = findViewById(R.id.editview_sno)
        val iphone: EditText = findViewById(R.id.editview_iphone)
        val number: EditText = findViewById(R.id.editview_number)
        val password: EditText = findViewById(R.id.editview_password)

        val dbHelper = MyDatabaseHelper(this, "healthy.db", 2)
        val storage: Button = findViewById(R.id.button_OK)
        storage.setOnClickListener {
            val db = dbHelper.writableDatabase
            val values03 = ContentValues().apply {
                put("name", name.text.toString())
                put("sno", sno.text.toString())
                put("iphone", iphone.text.toString())
                put("number", number.text.toString())
                put("password", password.text.toString())

            }
            Toast.makeText(this, "Succeeding create", Toast.LENGTH_SHORT).show()
            db.insert("user", null, values03)
        }
        //返回
        val returnL: Button = findViewById(R.id.button_return)
        returnL.setOnClickListener {
            finish()
        }
    }
}