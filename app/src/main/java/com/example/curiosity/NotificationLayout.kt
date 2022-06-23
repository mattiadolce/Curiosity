package com.example.curiosity

import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class NotificationLayout : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_layout)

        Log.i("metto" , "leggo - non la sapeva ")

        auth = Firebase.auth
        val email = auth.currentUser?.email?.replace(".","")

        var nTotali = CuriosityUsersHelper.totalAnswer
        nTotali += 1


        CuriosityUsersHelper.updateUserItem(email.toString(),"totalAnswer",nTotali.toString())

        val  manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        manager.cancelAll()

        finish()
    }
}