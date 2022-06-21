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
            .getInstance("https://curiosity-ca522-default-rtdb.firebaseio.com/")
        val refUsers =    db.getReference("Users")
        val refCuriosity =    db.getReference("Curiosity")

        fun readUsersItems(toDoEventListener: ValueEventListener){
            refUsers.addValueEventListener(toDoEventListener)
        }

        fun readCuriosityItems(toDoEventListener: ValueEventListener){
            refCuriosity.addValueEventListener(toDoEventListener)
        }

        fun setUsersItem(key: String, user : User){
            refUsers.child(key).setValue(user)
        }

        fun updateUserItem(key: String ,nodeName:String, nodeValue: String)
        {
            refUsers.child(key).child(nodeName).setValue(nodeValue)
                .addOnSuccessListener {


                    Log.i("sETTINGS", "$nodeName correttamente aggiornato")
                }
                .addOnFailureListener {
                    Log.i("sETTINGS", "$nodeName non aggiornato")
                }
        }

        fun removeUsersItem(key: String){
            refUsers.child(key).removeValue()
        }

        fun md5(input:String): String {
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
        }
    }
}