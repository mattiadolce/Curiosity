package com.example.curiosity

import android.util.Log
import android.widget.ArrayAdapter
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_settings.*
import java.math.BigInteger
import java.security.MessageDigest

class CuriosityUsersHelper {
    companion object{
        //"creaimo" il db
        val db = FirebaseDatabase
            .getInstance("https://curiosity-ca522-default-rtdb.firebaseio.com/")
        val refUsers =    db.getReference("Users")
        val refCuriosity =    db.getReference("Curiosity")
        var curiosita : String = ""

        var listaAreeInteresse  = mutableListOf<String>()

        var totalCorrectAnswer = 0
        var totalAnswer = 0

        //HarryPotter0 : curiosita_hp 0
        //HarryPotter1 : curiosita_hp 1
        //Sport0       : curiosita_sport 0
        var mapCuriosita = mutableMapOf<String,String>()

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

            if(nodeName == "aree_interesse")
            {
                listaAreeInteresse.clear()
            }

            refUsers.child(key).child(nodeName).setValue(nodeValue)
                .addOnSuccessListener {
                    Log.i("sETTINGS", "$nodeName correttamente aggiornato")
                }
                .addOnFailureListener {
                    Log.i("sETTINGS", "$nodeName non aggiornato")
                }
        }



        fun getAreeInteresse(key: String)
        {
            //Lettura dal database firebase - Nodo generale Users
            val addValueEventListener2 = CuriosityUsersHelper.refUsers.child(key)
                .child("aree_interesse").
                addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    var areeInteresseFirebaseList = dataSnapshot.value.toString()


                    areeInteresseFirebaseList?.split(", ")?.forEach(){
                        Log.i("234222", it)

                        if(it.equals("") || it == null) return
                        listaAreeInteresse.add(it)

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("SettingsFragment", "Failed to read value.", error.toException())
                }
            })
        }

        fun initialize(key: String) {
            getCorrette(key)
            getTotali(key)
            getAreeInteresse(key)
        }
        fun getCorrette(key: String)
        {
            //Lettura dal database firebase - Nodo generale Users
            val addValueEventListener3 = CuriosityUsersHelper.refUsers.child(key)
                .child("correctAnswer").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if(!dataSnapshot?.value.toString().equals(""))
                    {
                        totalCorrectAnswer = Integer.parseInt(dataSnapshot?.value.toString())
                        Log.i("aadasdaad","l'utente  corrette" + totalCorrectAnswer)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("SettingsFragment", "Failed to read value.", error.toException())
                }
            })
        }

        fun getTotali(key: String)
        {
            //Lettura dal database firebase - Nodo generale Users
            val addValueEventListener2 = CuriosityUsersHelper.refUsers.child(key).child("totalAnswer").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if(!dataSnapshot?.value.toString().equals("")) {
                        totalAnswer = Integer.parseInt(dataSnapshot.value.toString())
                        Log.i("SettingsFragment", "l'utente  totalAnswer" + totalAnswer)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("SettingsFragment", "Failed to read value.", error.toException())
                }
            })
        }


        fun listen(nodeName : String, nodeFiglio : String) {

            val addValueEventListener = CuriosityUsersHelper.refCuriosity.child(nodeName).addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    curiosita = dataSnapshot.child(nodeFiglio).getValue().toString()


                    mapCuriosita.put(nodeFiglio,curiosita)

                    //notificationTimeSelected = dataSnapshot.value.toString()
                    //Log.i("SettingsFragment","l'utente vuole ricevere notifiche ogni" + notificationTimeSelected)
                    //list_tempo?.setItemChecked(mapConversionTempo[notificationTimeSelected]!!,true)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("SettingsFragment", "Failed to read value.", error.toException())
                }
            })
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