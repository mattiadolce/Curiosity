package com.example.curiosity

import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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

        fun removeUsersItem(key: String){
            ref.child(key).removeValue()
        }
    }
}