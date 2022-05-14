package com.example.curiosity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        thread(){

            Log.i("MainThread","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")

        }
    }
}