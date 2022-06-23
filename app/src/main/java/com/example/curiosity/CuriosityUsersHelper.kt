package com.example.curiosity

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_settings.*
import java.math.BigInteger
import java.security.MessageDigest

class CuriosityUsersHelper {
    companion object{

        private lateinit var curiosityActivity : CuriosityActivity

        //"creaimo" il db
        val db = FirebaseDatabase
            .getInstance("https://curiosity-ca522-default-rtdb.firebaseio.com/")
        val refUsers =    db.getReference("Users")
        val refCuriosity =    db.getReference("Curiosity")
        var curiosita : String = ""

        var listaAreeInteresse  = mutableListOf<String>()

        var totalCorrectAnswer = 0
        var totalAnswer = 0

        var tempoMinutiNotifica : String = ""

        //HarryPotter0 : curiosita_hp 0
        //HarryPotter1 : curiosita_hp 1
        //Sport0       : curiosita_sport 0
        var mapCuriosita = mutableMapOf<String,String>()

        fun readUsersItems(toDoEventListener: ValueEventListener){
            refUsers.addValueEventListener(toDoEventListener)
        }

        fun initializeCuriosity(curiosityActivity1 : CuriosityActivity)
        {
            curiosityActivity = curiosityActivity1
        }

        fun readUsersAreeInteresse(toDoEventListener: ValueEventListener){
            refUsers.child("aree_interesse").addValueEventListener(toDoEventListener)
        }

        fun readUsersNode(toDoEventListener: ValueEventListener, key: String,nodeName : String ){
            refUsers.child(key).addValueEventListener(toDoEventListener)
        }

        fun readUsersTotal(toDoEventListener: ValueEventListener){
            refUsers.child("aree_interesse").addValueEventListener(toDoEventListener)
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

            if(nodeName.equals("aree_interesse"))
            {
                listaAreeInteresse.clear()

                nodeValue?.split(", ")?.forEach(){
                    Log.i("5543w", it)

                    if(it.equals("") || it == null) return
                    listaAreeInteresse.add(it)

                }
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

                            if(!it.equals("") && it != null) {
                                for(i in 0..9)
                                {
                                    listen(it,it + "$i")
                                }
                            }


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
            getTempoNotifica(key)
        }

        private fun getTempoNotifica(key: String) {
            //Lettura dal database firebase - Nodo generale Users
            val addValueEventListener3 = CuriosityUsersHelper.refUsers.child(key)
                .child("tempoMinutiNotifica").addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if(!dataSnapshot?.value.toString().equals(""))
                        {
                            tempoMinutiNotifica = dataSnapshot?.value.toString()
                            create_and_schedule()
                            Log.i("aadasdaad","l'utente  tempoMinutiNotifica " + tempoMinutiNotifica)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Failed to read value
                        Log.w("SettingsFragment", "Failed to read value.", error.toException())
                    }
                })
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

        fun create_and_schedule()
        {
            Log.i("creoeschedulo","creoeschedulo")
            curiosityActivity.createNotificationChannel()
            curiosityActivity.scheduleNotification()
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