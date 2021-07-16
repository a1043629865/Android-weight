package com.example.practiceproject

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class UserActivity(private var username: String) : Fragment() {

    private lateinit var userPhoto: Button
    private lateinit var userAccount: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_user, container, false)
        userPhoto = view.findViewById(R.id.user_photo)
        userAccount = view.findViewById(R.id.account)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        userAccount.text = username


        userPhoto.setOnClickListener {
            val intent = Intent(MyApplication.context, UserDetailActivity::class.java)
            intent.putExtra("number", username)
            startActivity(intent)
        }


    }
}