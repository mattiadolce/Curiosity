package com.example.curiosity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class NotificationLayoutSapeva : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_layout_sapeva)

        Log.i("metto" , "leggo - la sapeva ")

        auth = Firebase.auth
        val email = auth.currentUser?.email?.replace(".","")

        var nTotali = CuriosityUsersHelper.totalAnswer
        var nCorrette = CuriosityUsersHelper.totalCorrectAnswer
        nCorrette += 1

        nTotali += 1

        CuriosityUsersHelper.updateUserItem(email.toString(),"correctAnswer",nCorrette.toString())
        CuriosityUsersHelper.updateUserItem(email.toString(),"totalAnswer",nTotali.toString())

        finish()

    }
}