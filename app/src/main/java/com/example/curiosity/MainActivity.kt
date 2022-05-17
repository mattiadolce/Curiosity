package com.example.curiosity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val transaction = supportFragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainerView,LoginFragment())
    }
}