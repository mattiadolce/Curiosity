package com.example.curiosity

import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import java.math.BigInteger
import java.security.MessageDigest

class CuriosityUsersHelper {
    companion object{
        //"creaimo" il db
        val db = FirebaseDatabase
            .getInstance("https://curiosityusers2-default-rtdb.firebaseio.com/")
        private val ref =    db.getReference("users")

        fun readUsersItems(toDoEventListener: ValueEventListener){
            ref.addValueEventListener(toDoEventListener)
        }

        /*
        fun setUsersItem(key:String, email: String){
            db.child(key).setValue(email) //scriviamo come sottonodo di Users un figlio con valore email (key)
        }
         */

        fun setUsersItem(key: String, user : User){
            ref.child(key).setValue(user)
        }

        fun updateUserItem(key: String , nodeValue: String)
        {
            Log.i("chiamata","chiamata a updateuseritem")
            ref.child(key).child("aree_interesse").setValue(nodeValue)
                .addOnSuccessListener {
                    Log.i("sETTINGS", "WRite ok")
                }
                .addOnFailureListener {
                    Log.i("sETTINGS", "WRite failed")
                }
        }

        fun removeUsersItem(key: String){
            ref.child(key).removeValue()
        }

        fun md5(input:String): String {
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
        }
    }
}