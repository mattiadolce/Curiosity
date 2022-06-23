package com.example.curiosity

import android.annotation.SuppressLint
import android.app.*
import android.app.Notification.VISIBILITY_PUBLIC
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC as VP1
import java.util.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction = supportFragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainerView,LoginFragment())


        //actionbar.setHomeAsUpIndicator(R.drawable.ic_menu)


    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }



}