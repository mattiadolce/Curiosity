package com.example.curiosity

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_settings.*

class GeneratorFragment : Fragment() {
    var areeInteresseFirebaseList : String? = null
    var auth: FirebaseAuth = Firebase.auth
    var listaAree : ArrayList<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_generator, container, false)
    }


    //Lettura dal database firebase - Nodo generale Users
    val addValueEventListener2 = CuriosityUsersHelper.refUsers.child(auth.currentUser?.email?.replace(".","").toString()).child("aree_interesse").addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {

            areeInteresseFirebaseList = dataSnapshot.value.toString()

            Log.i("SettingsFragment","l'utente  sembrea essere interessato a" + areeInteresseFirebaseList)

            areeInteresseFirebaseList?.split(", ")?.forEach(){
                Log.i("SettingsFragment", it)



                if(it.equals("") || it == null) return

                listaAree!!.add(it.toString())

            }
        }

        override fun onCancelled(error: DatabaseError) {
            // Failed to read value
            Log.w("GeneratorFragment", "Failed to read value.", error.toException())
        }
    })


    //Lettura dal database firebase - Nodo generale Users


    val addValueEventListener = CuriosityUsersHelper.refCuriosity.child(listaAree!!.get(0)).addValueEventListener(object :
        ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            Log.i("CHILDREN COUNT", dataSnapshot.childrenCount.toString())
            Log.i("AAAAAAAAAAA", dataSnapshot.child(listaAree!!.get(0) + 0).getValue().toString())
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