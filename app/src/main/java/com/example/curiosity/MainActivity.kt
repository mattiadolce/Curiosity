package com.example.curiosity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import kotlin.concurrent.thread
import com.example.curiosity.LoginFragment as LoginFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val transaction = supportFragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainerView,LoginFragment())
    }
}