package com.example.curiosity

import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.FirebaseDatabase

class CuriosityUsersHelper {
    companion object{
        //"creaimo" il db
        private val db = FirebaseDatabase
            .getInstance("https://curiosityusers-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("Users")

        fun readUsersItems(toDoEventListener: ChildEventListener){
            db.addChildEventListener(toDoEventListener)
        }

        /*
        fun setUsersItem(key:String, email: String){
            db.child(key).setValue(email) //scriviamo come sottonodo di Users un figlio con valore email (key)
        }
         */

        fun setUsersItem(user : User){
            val userId = db.push().key!!

            db.child(userId).setValue(user).addOnSuccessListener {
                Log.i("DATABASE", "utente inserito")
            }.addOnFailureListener{
                Log.i("DATABASE", "ERRORE INSERIMENTO utente ")

            }
        }

        fun removeUsersItem(key: String){
            db.child(key).removeValue()
        }
    }
}